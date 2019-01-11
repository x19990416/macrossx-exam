import React, { PureComponent,Fragment   } from 'react';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';
import StandardTable from '@/components/StandardTable';
import { connect } from 'dva';
import { Link } from 'dva/router';
import ModalForm from '@/Common/ModalForm'

import { Card, Form, Row, Col, Input, Button, Icon, Select, Divider,Dropdown,Menu  } from 'antd';

import styles from './Question.less';

const PAGE = 'question'


@Form.create()
@connect(({ question }) => ({
  data:question.data,
  result:question.result,
  modalBody:question.modalBody
}))
class QuestionGeneral extends PureComponent {
  state={
     selectedRows: [],
     modalVisible: false,
     modalProps: {},  
     modalBody:{
      body:[]
     },     
     modalInit:false
  }

  columns = [{
  title: '题目',
    dataIndex: 'name',
  },{
    title:'题型',
    dataIndex:'questionTypeName'
  },{
    title: '备注',
    dataIndex: 'comment',
  },{
    title: '操作',
    render: (text, record) => (      
      <Fragment>
        <a onClick={()=>this.handleUpdateModalVisible(true,record)}>修改</a>
        <Divider type="vertical" />
        <a onClick={()=>this.handleDelete(record)}>删除</a>
      </Fragment>
    ),},
  ];

  componentDidMount(){
    const { dispatch } = this.props;    
    dispatch({
      type: 'question/list',
      payload: {},
      page: PAGE
    });
    this.props.dispatch({
        type:'question/initModual',
        payload:{'pageSize':10000,'pageNo':0},
        page:PAGE})
    dispatch({
      type: 'question/fetchQuestionType',
      payload: {},      
      page:PAGE,
    })  

  }

  handleSearch = e =>{
    e.preventDefault();    
    this.props.form.validateFields((err, fieldsValue) => {
      if (err) return;
      const values = {
        ...fieldsValue,        
      };

      this.props.dispatch({
        type: 'question/list',
        payload: values,
        page:PAGE
      });
    });
  }

  handleDelete = (row) =>{        
      this.props.dispatch({
        type: 'question/remove',
        payload:{"key":row.key},
        page: PAGE
      })      
  }

  handleSelectRows = rows => {
    this.setState({
      selectedRows: rows,
    });
  };

  handleModalVisible = (flag,modalProps) =>{    
    this.setState({      
      modalVisible: !!flag,      
    })
    if(modalProps!=null){
      this.setState({
        modalProps: modalProps
      })
    }
    this.setState({
      modalBody:{
        body:[]
      }
      
    })
  }

  handleChange =(page,pageSize)=>{        
    const { dispatch, form } = this.props;
    dispatch({
      type: 'question/list',
      payload: {pageSize:page.pageSize,pageNo:page.current-1},
      page:PAGE
    });
  };

  handleSubmit =(data)=>{      
    console.log('handleSubmit',data)
    let type ='question/add'    
    if('修改题目' === this.state.modalProps.title){
      type='question/update'
      Object.entries(data).forEach(e=>{
        if(data[e[0]] ===this.state.currentRecord[e[0]] && (!e[0].startsWith('choice_') && e[0]!='name')){
          data[e[0]] = null
        }
      })
      data = {
        ...data,
        key:this.state.modalProps.key
      }

    }    
    let choiceList = {}
    Object.entries(data).filter(e=>{      
      return e[0].startsWith('choice_')
    }).forEach(e=>{
      let key = e[0].split('_')[1]                
      choiceList[key] = e[1]      
    })
    let answer =data.answer ==null?null: [data.answer]
    let requestData = {};    
    let content = {};    
    content['choiceList'] = choiceList;
    content['title']  = data.name;
    requestData = {
      'content':content,
      'name':data.name,
      'questionType':data.questionType,
      'answer':answer,
      'points':data.points,
      'key':data.key,
      'analysis':data.analysis

    }

    this.props.dispatch({
        type: type,
        payload: requestData,
        page:PAGE
      });     
    this.handleModalVisible(false)
    
  }



  handleUpdateModalVisible = (flag,record) => {        
    this.setState({
      currentRecord:record
    })
    Object.entries(record.content.choiceList).forEach(e=>{
      record['choice_'+e[0]]=e[1]
    })

    if(record.questionType===1){
      record.answer = record.answer[0]
    }    
    this.setState({
      modalVisible: flag,
      modalProps: {
        ...record,
        title:'修改题目'
      }
    });    
    this.onModualSelect(''+record.questionType)
  };

  onModualSelect = (e) =>{
      this.props.dispatch({
      type: 'question/updateModual',
      payload: e,
      page:PAGE
    });        
  }


  renderSearchForm(){
    const {
      form: { getFieldDecorator },
    } = this.props;
    return (
      <Form  layout="inline" onSubmit={this.handleSearch}>
        <Row gutter={{ md: 8, lg: 24, xl: 48 }}>
          <Col md={8} sm={24}>
            <Form.Item label="题目">
                {getFieldDecorator('name')(<Input placeholder="请输入"/>)}
            </Form.Item>
          </Col>
          <Col md={8} sm={24}>
            <Form.Item label="备注">
              {getFieldDecorator('comment')(<Input placeholder="请输入"/>)}
            </Form.Item>
          </Col>
          <Col md={8} sm={24}>
            <span>
              <Button type="primary" htmlType="submit">
                查询
              </Button>
              <Button style={{ marginLeft: 8 }} >
                重置
              </Button>
            </span>
          </Col>          
        </Row>
      </Form>
    )
  }


  render() {
    const menu = (
      <Menu >
        <Menu.Item key="remove">删除</Menu.Item>
        <Menu.Item key="approval">批量审批</Menu.Item>
      </Menu>
    );

    const { selectedRows} = this.state    
    let modalBody = this.props.modalBody;
    for(let body of modalBody.body){
      if('select'==body.itemType )  {
        body.onSelect = this.onModualSelect
        break;
      }
    }

    console.log(modalBody)

    this.setState({
      modalBody:modalBody
    })
  
    return (
      <PageHeaderWrapper title="试题管理" >
       <Card bordered={false}>
        <div className={styles.tableList}>
          <div className={styles.tableListForm}>{this.renderSearchForm()}</div>
        
          <div className={styles.tableListOperator}>
            <Button icon="plus" type="primary" onClick={()=>this.handleModalVisible(true,{title:'新增题库'})} >新建</Button>
            {selectedRows.length > 0 && (
            <span>
              <Dropdown overlay={menu}>
                <Button>
                  更多操作 <Icon type="down" />
                </Button>
              </Dropdown>
            </span>
            )}
          </div>
          <StandardTable columns={this.columns}  data={this.props.data} selectedRows={selectedRows} onSelectRow={this.handleSelectRows} onChange={this.handleChange}/>        
        </div>
      </Card> 
      <ModalForm {...this.state.modalBody} modalVisible={this.state.modalVisible} modalProps= {this.state.modalProps} onCancel={()=>this.handleModalVisible(false)} onSubmit={(data)=>{this.handleSubmit(data)}}> </ModalForm>      
    </PageHeaderWrapper> 
  )}
}

export default QuestionGeneral
