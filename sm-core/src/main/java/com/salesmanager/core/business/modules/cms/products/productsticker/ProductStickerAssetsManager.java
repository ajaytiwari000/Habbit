package com.salesmanager.core.business.modules.cms.products.productsticker;

import com.salesmanager.core.business.modules.cms.FileImageGet;
import com.salesmanager.core.business.modules.cms.FileImagePut;
import com.salesmanager.core.business.modules.cms.FileImageRemove;
import com.salesmanager.core.business.modules.cms.common.AssetsManager;
import java.io.Serializable;

public interface ProductStickerAssetsManager<Parent, Child>
    extends AssetsManager,
        FileImageGet<Parent, Child>,
        FileImagePut<Child>,
        FileImageRemove<Child>,
        Serializable {}
