package com.salesmanager.core.business.modules.cms.attributes.Pack;

import com.salesmanager.core.business.modules.cms.FileImageGet;
import com.salesmanager.core.business.modules.cms.FileImagePut;
import com.salesmanager.core.business.modules.cms.FileImageRemove;

public abstract class PackFileManager<Parent, Child>
    implements FileImagePut<Child>, FileImageRemove<Child>, FileImageGet<Parent, Child> {}
