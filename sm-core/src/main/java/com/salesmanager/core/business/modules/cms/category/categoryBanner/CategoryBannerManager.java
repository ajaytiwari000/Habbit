package com.salesmanager.core.business.modules.cms.category.categoryBanner;

import com.salesmanager.core.business.modules.cms.*;

public abstract class CategoryBannerManager<Parent, Child>
    implements FileImagePut<Child>,
        FileImagesRemove<Parent>,
        FileImageRemove<Child>,
        FileImagesGet<Parent, Child> {}
