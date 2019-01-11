import React, { PureComponent,Fragment   } from 'react';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';
import StandardTable from '@/components/StandardTable';
import { connect } from 'dva';
import { Link } from 'dva/router';
import ModalForm from '@/Common/ModalForm'

import { Card, Form, Row, Col, Input, Button, Icon, Select, Divider,Dropdown,Menu  } from 'antd';

import styles from './Question.less';

const PAGE = 'field'

const modalBody ={'body':[{'name':'题库名','id':'name','itemType':'input',rules:[{ required: true, message: '请输入至少x个字符的规则描述！', min: 5 }]},{'name':'备注','field':'comment'}]}

@Form.create()
@connect(({ question }) => ({
  data:question.data,
  result:question.result
}))
class QuestionGeneral extends PureComponent {
  state={
     selectedRows: [],
     modalVisible: false,
     modalProps: {},        
  }

  columns = [{
  title: '题库名',
    dataIndex: 'name',
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

  refreshPage = ()=>{
    if(this.props.data){
    this.props.dispatch({
      type: 'question/questionFieldList',
      payload: {}
    });
   }
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
  }

  handleChange =(page,pageSize)=>{        
    const { dispatch, form } = this.props;
    dispatch({
      type: 'question/questionFieldList',
      payload: {pageSize:page.pageSize,pageNo:page.current-1}
    });
  };

  handleSubmit =(data)=>{  
    let type ='question/addField'
    if('修改题库' === this.state.modalProps.title){
      type='question/updateField'
      data = {
        ...data,
        key:this.state.modalProps.key
      }
    }
    this.props.dispatch({
        type: type,
        payload: data
      });     
    this.handleModalVisible(false)
    this.refreshPage()
  }



  handleUpdateModalVisible = (flag,record) => {    
    this.setState({
      modalVisible: flag,
      modalProps: {
        ...record,
        title:'修改题库'
      }
    });    
  };

  renderSearchForm(){
    const {
      form: { getFieldDecorator },
    } = this.props;
    return (
      <Form  layout="inline" onSubmit={this.handleSearch}>
        <Row gutter={{ md: 8, lg: 24, xl: 48 }}>
          <Col md={8} sm={24}>
            <Form.Item label="题库名">
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
      <ModalForm {...modalBody} modalVisible={this.state.modalVisible} modalProps= {this.state.modalProps} onCancel={()=>this.handleModalVisible(false)} onSubmit={(data)=>{this.handleSubmit(data)}}> </ModalForm>      
    </PageHeaderWrapper> 
  )}
}

export default QuestionGeneral
