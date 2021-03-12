package com.salesmanager.core.modules.utils;

import com.salesmanager.core.model.common.Addresss;

public interface GeoLocation {

  Addresss getAddress(String ipAddress) throws Exception;
}
