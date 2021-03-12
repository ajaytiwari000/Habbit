package com.salesmanager.core.business.repositories.customer.aws;

import com.salesmanager.core.business.modules.cms.FileImagePut;
import com.salesmanager.core.business.modules.cms.FileImageRemove;
import com.salesmanager.core.business.modules.cms.common.AssetsManager;
import java.io.Serializable;

public interface CustomerAssetsManager<Child>
    extends AssetsManager, FileImagePut<Child>, FileImageRemove<Child>, Serializable {}
