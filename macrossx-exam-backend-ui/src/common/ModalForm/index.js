import React, { PureComponent,Fragment   } from 'react';
import { Modal } from 'antd';

import { Select,Card, Form, Row, Col, Input, Button, Icon, Divider,Dropdown,Menu ,Option,Radio, TreeSelect  } from 'antd';


const formItemLayout = {
labelCol:{ span: 5 }, wrapperCol:{ span: 15 }
};
const formItemLayoutWithOutLabel = {
      wrapperCol: {
        span: 15, offset: 5}
};   


const treeData = [{
  title: 'Node1',
  value: '0-0',
  key: '0-0',
  children: [{
    title: 'Child Node1',
    value: '0-0-1',
    key: '0-0-1',
  }, {
    title: 'Child Node2',
    value: '0-0-2',
    key: '0-0-2',
  }],
}, {
  title: 'Node2',
  value: '0-1',
  key: '0-1',
  children:[]
}];

@Form.create()
class ModalForm extends PureComponent{
  constructor(props) {
    super(props);    
  }

  state = {
    modalVisible:false,
    modalProps:{
      title:null,            
    },
  };

  okHandle = () => {    
    this.props.form.validateFields((err, fieldsValue) => {      
      if (err) return;
      this.props.onSubmit(fieldsValue);
      this.props.form.resetFields();            
      
    });
  };

  radioGroup = (e) =>{
    return <Radio.Group onChange={this.onChange}>
          {
            e.options.map((option,index)=>{
              return <Radio key={index} value={option.value}>{option.name}</Radio>
            })
          }
          </Radio.Group>
  }
  
  treeSelect = (e) =>{
    console.log('xxxxxxxxxxxxxxxx',e.options)
    return <TreeSelect
      treeCheckable ={true} treeData={e.options}  style={{ width: '100%' }}
      placeholder='Please select'/ >
 
  }


  input = (e) =>{
    let value = this.state.modalProps[e.id]==null?null:this.state.modalProps[e.id];  
    return <Input placeholder={value==null?'请输入':value} />
  }

  textArea = (e) =>{
    let value = this.state.modalProps[e.id]==null?null:this.state.modalProps[e.id];  
    return <Input.TextArea placeholder={value==null?'请输入':value} autosize />
  }

  select = (e) =>{  
    let value = this.state.modalProps[e.id]==null?null:this.state.modalProps[e.id];      
    return <Select placeholder={value} style={{ width: '100%' }} onSelect={e.onSelect} >{e.options.map(o=>          
       <Select.Option key={o.key} value={o.key}> {o.name}</Select.Option>
    )}</Select>
  }


  itemGenerator = (e,index) =>{   
  
    let item = null,
    initValue = this.state.modalProps[e.id]==null?null:this.state.modalProps[e.id];     
    switch(e.itemType){
      case "input": 
        item = this.input(e)
        break;
      
      case "select":
      item = this.select(e);        
      break;      
      case "radioGroup": {        
        item = this.radioGroup(e);
        break;
      }
      case 'textArea':{
        item = this.textArea(e)
        break;
      }
      case 'treeSelect':{
        item = this.treeSelect(e)
        break;
      }
    }     

    if(item!=null){
    return <Form.Item {...(''===e.name?formItemLayoutWithOutLabel:formItemLayout)}  key={index}  label={e.name} >      
        {this.props.form.getFieldDecorator(''+e.id,{initialValue: initValue, 
          rules:e.rules==null?{}:e.rules,
        })(item)}
    </Form.Item>;
    }
    
    
  }

  render(){            
    if (this.props.modalProps!=null){this.setState({modalProps:this.props.modalProps})}    
    if (this.props.modalVisible!=null){this.setState({modalVisible:this.props.modalVisible})}    
    const formItem = this.props.body.map((e,index)=>{ 
      return this.itemGenerator(e,index);      
    })        
    return (
    <Modal
      destroyOnClose
      title= {this.state.modalProps.title}
      visible={this.state.modalVisible}
      onCancel={() => this.props.onCancel()}
      onOk = {this.okHandle}
    >{formItem}
    </Modal>
    );
  }


}

export default ModalForm
