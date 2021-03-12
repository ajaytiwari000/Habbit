package com.salesmanager.core.model.catalog.product;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import java.io.InputStream;
import java.util.Objects;
import javax.persistence.*;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "NUTRIENTS_INFO", schema = SchemaConstant.SALESMANAGER_SCHEMA)
public class NutrientsInfo extends SalesManagerEntity<Long, NutrientsInfo> {

  private static final long serialVersionUID = 123456098700L;

  @Id
  @Column(name = "NUTRIENTS_INFO_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "CATEGORY_NUTRIENTS_INFO_SEQ_NEXT_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @Column(name = "IMAGE")
  private String img;

  @Column(name = "IMAGE_URL")
  private String imgUrl;

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "ORDER_NUMBER")
  private int orderNumber = 0;

  @Transient private InputStream image = null;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }

  public InputStream getImage() {
    return image;
  }

  public void setImage(InputStream image) {
    this.image = image;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(int orderNumber) {
    this.orderNumber = orderNumber;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    NutrientsInfo that = (NutrientsInfo) o;
    return Objects.equals(description, that.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), description);
  }
}
