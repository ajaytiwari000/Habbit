package com.salesmanager.core.business.modules.cms.attributes.NutrientsInfo;

import com.salesmanager.core.business.modules.cms.FileImagePut;
import com.salesmanager.core.business.modules.cms.FileImageRemove;
import com.salesmanager.core.business.modules.cms.FileImagesGet;
import com.salesmanager.core.business.modules.cms.FileImagesRemove;

public abstract class NutrientsInfoManager<Parent, Child>
    implements FileImagePut<Child>,
        FileImagesRemove<Parent>,
        FileImageRemove<Child>,
        FileImagesGet<Parent, Child> {}
