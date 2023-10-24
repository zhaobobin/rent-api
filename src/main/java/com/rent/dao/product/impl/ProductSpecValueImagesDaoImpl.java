package com.rent.dao.product.impl;

import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.ProductSpecValueImagesDao;
import com.rent.mapper.product.ProductSpecValueImagesMapper;
import com.rent.model.product.ProductSpecValueImages;
import org.springframework.stereotype.Repository;

/**
 * ProductSpecValueImagesDao
 *
 * @author youruo
 * @Date 2020-06-16 15:34
 */
@Repository
public class ProductSpecValueImagesDaoImpl
    extends AbstractBaseDaoImpl<ProductSpecValueImages, ProductSpecValueImagesMapper>
    implements ProductSpecValueImagesDao {

}
