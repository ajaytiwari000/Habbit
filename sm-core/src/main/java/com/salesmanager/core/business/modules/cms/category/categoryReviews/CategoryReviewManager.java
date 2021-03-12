package com.salesmanager.core.business.modules.cms.category.categoryReviews;

import com.salesmanager.core.business.modules.cms.FileImagePut;
import com.salesmanager.core.business.modules.cms.FileImageRemove;
import com.salesmanager.core.business.modules.cms.FileImagesGet;
import com.salesmanager.core.business.modules.cms.FileImagesRemove;

public abstract class CategoryReviewManager<Parent, Child>
    implements FileImagePut<Child>,
        FileImagesRemove<Parent>,
        FileImageRemove<Child>,
        FileImagesGet<Parent, Child> {}
