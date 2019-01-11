/*
 * Copyright (C) 2018 The macrossx-exam-service Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.x19990416.macrossx.exam.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.x19990416.macrossx.exam.data.domain.Role;
import com.github.x19990416.macrossx.exam.data.persistence.MeUserRole;
import com.github.x19990416.macrossx.exam.data.repository.MeRoleRepository;
import com.github.x19990416.macrossx.exam.data.repository.MeUserRoleRepository;

@Service
public class RoleService implements IRoleService {
  
  @Autowired
  MeRoleRepository meRoleRep;
  @Autowired
  MeUserRoleRepository meUserRoleRep;
  
  @Override
  public Optional<Role> find(String authority) {
    return meRoleRep.findByAuthority(authority).flatMap((e)->Optional.of(new Role(e)));
  }

  @Override
  public Optional<Role> find(Long roleId) {
    return meRoleRep.findById(roleId).flatMap((e)->Optional.of(new Role(e)));
  }
  
  public List<Role> find() {
    return meRoleRep.findAll().stream().map(e->{return new Role(e);}).collect(Collectors.toList());
  }
  
  public boolean addUserRole(String userId,Long roleId) {
    MeUserRole meUserRole = new MeUserRole();
    meUserRole.setUserId(userId);
    meUserRole.setRoleId(roleId);
    meUserRole = meUserRoleRep.save(meUserRole);
    return meUserRole == null; 
  }
  
  public List<Role> findUserRole(String userId){
    List<Long> roleIds = Lists.newArrayList();
    meUserRoleRep.findByUserId(userId).forEach(e->{
      roleIds.add(e.getRoleId());
    });
    return  meRoleRep.findAllById(roleIds).stream().map(e->{return new Role(e);}).collect(Collectors.toList());
  }    
  

}
