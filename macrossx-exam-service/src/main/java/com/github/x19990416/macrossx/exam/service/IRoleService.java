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
import com.github.x19990416.macrossx.exam.data.domain.Role;

public interface IRoleService {
  public Optional<Role> find(String authority);
  public Optional<Role> find(Long roleId);
  public List<Role> find();
  public boolean addUserRole(String userId,Long roleId);
  public List<Role> findUserRole(String userId);
}
