package ca.uhn.fhir.jpa.dao.data;

import java.util.Collection;
import java.util.List;

/*
 * #%L
 * HAPI FHIR JPA Server
 * %%
 * Copyright (C) 2014 - 2020 University Health Network
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ca.uhn.fhir.jpa.model.entity.ForcedId;

public interface IForcedIdDao extends JpaRepository<ForcedId, Long> {

	// FIXME: JA We should log a performance warning if this is used since it's not indexed
	@Query("SELECT f.myResourcePid FROM ForcedId f WHERE myForcedId IN (:forced_id)")
	List<Long> findByForcedId(@Param("forced_id") Collection<String> theForcedId);

	@Query("SELECT f.myResourcePid FROM ForcedId f WHERE myResourceType = :resource_type AND myForcedId IN (:forced_id)")
	List<Long> findByTypeAndForcedId(@Param("resource_type") String theResourceType, @Param("forced_id") Collection<String> theForcedId);

	@Query("SELECT f FROM ForcedId f WHERE f.myResourcePid = :resource_pid")
	ForcedId findByResourcePid(@Param("resource_pid") Long theResourcePid);

	@Modifying
	@Query("DELETE FROM ForcedId t WHERE t.myId = :pid")
	void deleteByPid(@Param("pid") Long theId);
}
