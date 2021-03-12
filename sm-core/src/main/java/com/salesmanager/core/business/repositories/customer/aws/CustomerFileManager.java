package com.salesmanager.core.business.repositories.customer.aws;

import com.salesmanager.core.business.modules.cms.FileImagePut;
import com.salesmanager.core.business.modules.cms.FileImageRemove;

public abstract class CustomerFileManager<Child>
    implements FileImagePut<Child>, FileImageRemove<Child> {}
