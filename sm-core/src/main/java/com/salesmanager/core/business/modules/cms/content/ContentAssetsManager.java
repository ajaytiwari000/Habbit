package com.salesmanager.core.business.modules.cms.content;

import com.salesmanager.core.business.modules.cms.common.AssetsManager;
import java.io.Serializable;

public interface ContentAssetsManager
    extends AssetsManager, FileGet, FilePut, FileRemove, Serializable {}
