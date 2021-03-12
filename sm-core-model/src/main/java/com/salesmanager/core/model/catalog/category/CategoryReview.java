package com.salesmanager.core.model.catalog.category;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import java.io.InputStream;
import javax.persistence.*;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(
    name = "CATEGORY_REVIEW",
    schema = SchemaConstant.SALESMANAGER_SCHEMA,
    uniqueConstraints = @UniqueConstraint(columnNames = {"CATEGORY_REVIEW_ID"}))
public class CategoryReview extends SalesManagerEntity<Long, CategoryReview> {
  @Id
  @Column(name = "CATEGORY_REVIEW_ID", unique = true, nullable = false)
  @TableGenerator(
      name = "TABLE_GEN",
      table = "SM_SEQUENCER",
      pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT",
      pkColumnValue = "CATEGORY_REVIEW_SEQ_NEXT_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @Column(name = "REVIEWER_NAME")
  private String name;

  @Column(name = "IMAGE")
  private String img;

  @Column(name = "CATEGORY_NAME")
  private String categoryName;

  @Column(name = "IMAGE_URL")
  private String imageUrl;

  @Column(name = "ACHIEVEMENT")
  private String achievement;

  @Column(name = "REVIEW", length = 1024)
  private String review;

  @Column(name = "REVIEWS_RATING")
  private Double reviewRating;

  @Transient private InputStream image = null;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  public String getAchievement() {
    return achievement;
  }

  public void setAchievement(String achievement) {
    this.achievement = achievement;
  }

  public String getReview() {
    return review;
  }

  public void setReview(String review) {
    this.review = review;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public InputStream getImage() {
    return image;
  }

  public void setImage(InputStream image) {
    this.image = image;
  }

  public Double getReviewRating() {
    return reviewRating;
  }

  public void setReviewRating(Double reviewRating) {
    this.reviewRating = reviewRating;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }
}
