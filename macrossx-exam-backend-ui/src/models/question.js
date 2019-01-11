import { list,remove ,add,update, fieldNames,questionTypes} from '@/services/macrossx-exam-api';
import {message} from 'antd'




const _POINT_MODAL_BODY = {
  'body':[{'name':'知识点','id':'name','itemType':'input',rules:[{ required: true, message: '请输入题库名' }]},
    {'name':'题库名','id':'fieldId','itemType':'select',rules:[{ required: true, message: '请选择题库' }]},
    {'name':'备注','id':'comment','itemType':'input',rules:[{required:false}]},
  ]
}

const _QUESTION_MODAL_BODY = {
  'body':[{'name':'题目','id':'name','itemType':'input',rules:[{ required: true, message: '请输入题目' }]},
    {'name':'题型','itemType':'select','id':'questionType',rules:[{required:true,message:'请选择题型'}]},
    {'name':'解析','itemType':'textArea','id':'analysis',rules:[{required:false,message:'请输入答案解析'}]},
    {'name':'知识点','itemType':'treeSelect','id':'points',rules:[{required:false,message:'请选择知识点'}]},

     
  ]}

const QUESTION_CHOICE = {'name':'选项','id':'choice_','itemType':'input',rules:[{ required: true, message: '请输入选项' }]}
const QUESTION_SINGLE_CHOICE = {'name':'答案','id':'answer','itemType':'radioGroup','options':[{'name':'A','value':'A'},{'name':'B','value':'B'},{'name':'C','value':'C'},{'name':'D','value':'D'},],rules:[{ required: true, message: '请输入选项' }]}



export default {
  namespace: 'question',

  state: {
    notice: [],
    dataSource:[],
    selectedRows:[],    
    pagination:{
      showSizeChanger: true,
      showQuickJumper: true,
    },
    data:{dataSource:[],pagination:{}},    
    fields:[],
    modalBody:{
      'body':[]
    },
    points:null,

   },



  effects: {
    *list({payload,page}, { call, put }) {       
      const response = yield call(list,payload,page);
      yield put({
        type: 'showData',
        payload: Array.isArray(response.msg.content)?response.msg:[],
      });
    },

    *initModual({payload,page},{call,put}){
      if('question' === page){
        const response = yield call(list,payload,'point');
        let treeData = []
        let tmp={}
        if(response.code===100000){
          response.msg.content.forEach(e=>{
            console.log(e)
            if(tmp[''+e.fieldId]==null){
              tmp[''+e.fieldId]={}
              tmp[''+e.fieldId].title=e.fieldName
              tmp[''+e.fieldId].value = e.fieldId
              tmp[''+e.fieldId].key=e.fieldId
              tmp[''+e.fieldId].children=[]
            }
            let data = {}
            data.title = e.name;
            data.value = e.key;
            data.key = e.key
            tmp[''+e.fieldId].children.push(data)
          })          
        }
        Object.entries(tmp).forEach(e=>{          
          treeData.push(e[1])
        })

        yield put({
          type: 'doInitModual',
          payload: treeData,
          page:page,
        });              
      }
    },

    *updateModual({payload,page}, { call, put }) {  
      let data= {}            
      if('question'===page){
        payload = ''+payload
        switch(payload){
            case '1':              
              data = ['A','B','C','D'].map((e,index)=>{              
                let ret = {...QUESTION_CHOICE}
                ret.id =ret.id+e                
                if(index>0){
                  ret.name=''
                }              
                return ret
              });              
              QUESTION_SINGLE_CHOICE.options = [{'name':'A','value':'A'},{'name':'B','value':'B'},{'name':'C','value':'C'},{'name':'D','value':'D'}]
              data = data.concat(QUESTION_SINGLE_CHOICE)
              break;
            case '2':
              data = ['A','B','C','D','E','F',].map((e,index)=>{
                let ret = {...QUESTION_CHOICE}
                ret.id =ret.id+e
                if(index>0){
                  ret.name=''
                }
                return ret
              })              
              QUESTION_SINGLE_CHOICE.options = [{'name':'A','value':'A'},{'name':'B','value':'B'},{'name':'C','value':'C'},{'name':'D','value':'D'},{'name':'E','value':'E'},{'name':'F','value':'F'}]
               data = data.concat(QUESTION_SINGLE_CHOICE)
              break;
            
            case '3':              
              QUESTION_SINGLE_CHOICE.options = [{'name':'对','value':'T'},{'name':'错','value':'F'}] 
              data = QUESTION_SINGLE_CHOICE
              break;
          }        
        }
      console.log('updateModual',data)
      yield put({
        type: 'doModualUpdate',
        payload: data,
        page:page
      });

    },

    *remove({payload,page}, { call, put }) {  
      message.info("删除中...")
      let response = yield call (remove,payload,page);
      if(response.code===100000){
        message.destroy()     
        message.success('删除成功');
        response = yield call(list, {},page);        
        yield put({
        type: 'showData',
        payload: Array.isArray(response.msg.content)?response.msg:[],
       });                        
      }else{
        message.destroy()     
        message.error('删除失败',message.msg)
      }  
    },    

    *update({payload,page}, { call, put }) {  
      message.info("更新中...")
      let response = yield call (update,payload,page);
      if(response.code===100000){
        message.destroy()     
        message.success('更新成功');
        response = yield call(list, {},page);        
        yield put({
        type: 'showData',
        payload: Array.isArray(response.msg.content)?response.msg:[],
       });                        
      }else{
        message.destroy()     
        message.error('更新失败',message.msg)
      }    
    },

    *add({payload,page},{call,put}){
      message.info("添加中...")
      let response = yield call(add,payload,page)           
      if(response.code===100000){
        message.destroy()     
        message.success('添加成功');
        response = yield call(list, {},page);        
        yield put({
        type: 'showData',
        payload: Array.isArray(response.msg.content)?response.msg:[],
      });   
      }else{
        message.destroy()     
        message.error('更新失败',message.msg)
      }    
    },

    *fetchFieldsName({payload,page},{call,put}){
      const response = yield call(fieldNames,payload)  

      yield put({
        type: 'save',
        payload: response.code===100000?response.msg:[],  
        page:page,
        ref:'题库名'
      })
    },
    
    *fetchQuestionType({payload,page},{call,put}){
      const response = yield call(questionTypes,payload)  
      yield put({
        type: 'save',
        payload: response.code===100000?response.msg:[],  
        page:page,
        ref:'题型',
      })
    }
  },


  reducers: {
    showData(state,action) {         
      let pagination ={
        current:action.payload.pageNumber,
        pageSize:action.payload.pageSize,
        total:action.payload.count,        
      }

      let data={
        pagination:pagination,
        list:action.payload.content
      }
      return {
        ...state,
        data:data,        
      };
    }, 
    doInitModual(state,action){
      if('question' === action.page){
        _QUESTION_MODAL_BODY.body.forEach(e=>{
          if('points'===e.id){
            e.options = action.payload
          }
        })        
        return {
          ...state,
        }
      }


    },

    doModualUpdate(state,action){      
    if('question' ===action.page){
        let body = state.modalBody.body.filter(item=>{  
          return !(item.id.startsWith('choice_') || item.id=="answer")
        })      
        let retBody = []      
        body.forEach(e=>{
          retBody.push(e)
          if('select' == e.itemType){
            retBody = retBody.concat(action.payload)            
          }
        })      
        return {
          ...state,
          modalBody:{
            body:retBody
          }
        }
      }      
    },
    save(state,action){     
      let appendedData = state.modalBody;      
      if(appendedData.body.length==0){
      switch(action.page){
        case 'point':
          appendedData = _POINT_MODAL_BODY;
          break;
        case 'question':
          appendedData = _QUESTION_MODAL_BODY;
          break;        
        }
      }
      for(let body of appendedData.body){
        if('select'===body.itemType && body.name==action.ref)  {
          body.options=action.payload
          break;
        }
      }

      return {
        ...state,        
        modalBody:appendedData
      }
    },

  
  }
};
