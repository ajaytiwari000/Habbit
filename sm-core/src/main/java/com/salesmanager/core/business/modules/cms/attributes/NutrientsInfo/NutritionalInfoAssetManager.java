package com.salesmanager.core.business.modules.cms.attributes.NutrientsInfo;

import com.salesmanager.core.business.modules.cms.FileImageGet;
import com.salesmanager.core.business.modules.cms.FileImagePut;
import com.salesmanager.core.business.modules.cms.FileImageRemove;
import com.salesmanager.core.business.modules.cms.FileImagesRemove;
import com.salesmanager.core.business.modules.cms.common.AssetsManager;
import java.io.Serializable;

public interface NutritionalInfoAssetManager<Parent, Child>
    extends AssetsManager,
        FileImageGet<Parent, Child>,
        FileImagePut<Child>,
        FileImagesRemove<Parent>,
        FileImageRemove<Child>,
        Serializable {}
