package com.keliii.user.repository;

import com.keliii.user.entity.ControllerUrl;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by keliii on 2017/6/22.
 */
public interface ControllerUrlRepository extends JpaRepository<ControllerUrl, Integer> {
    ControllerUrl findByUrl(String url);

    @Transactional
    @Modifying
    @Query("update ControllerUrl c set c.isPublic = ?2 where c.id = ?1")
    int updatePublic(Integer id, Boolean isPublic);

    @Transactional
    @Modifying
    @Query("delete from ControllerUrl c where c.url not in (?1)")
    int deleteNoExists(List<String> urls);

}
