import request from '@/utils/request';

export async function list(params,path) { 
  console.log('call list\t',params,path)
  return request('/exam/backend/question/'+path+'/list',{
  	method:"POST",
  	body:params,
  });  
}

export async function fieldNames(params){
	return request('/exam/backend/question/field/name',{
		method:"POST",
		body:params
	})
}

export async function questionTypes(params){
    return request('/exam/backend/question/question/types',{
    method:"POST",
    body:params
  })
}

export async function remove(params,path) {  
  return request('/exam/backend/question/'+path+'/delete',{
  	method:"POST",
  	body:params,
  });  
}

export async function add(params,path) {
  return request('/exam/backend/question/'+path+'/add',{
  	method:"POST",
  	body:params,
  });  
}

export async function update(params,path) {
  return request('/exam/backend/question/'+path+'/update',{
  	method:"POST",
  	body:params,
  });  
}