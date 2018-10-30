package org.spu.Repository;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import org.spu.Uploads.Uploads;


@Repository

public interface RepositoryLog extends JpaRepository<Uploads, BigDecimal>

{


    List<Uploads> findByUploadsMetadataId(BigDecimal uploadsMetadataId);

    List<Uploads> findByUploadsMetadataRefNo(String refno);


}
