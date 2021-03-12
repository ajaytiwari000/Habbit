package com.salesmanager.shop.admin.model.web;

import com.salesmanager.core.model.system.MerchantConfiguration;
import java.util.List;

public class ConfigListWrapper {
  private List<MerchantConfiguration> merchantConfigs;

  public List<MerchantConfiguration> getMerchantConfigs() {
    return merchantConfigs;
  }

  public void setMerchantConfigs(List<MerchantConfiguration> merchantConfigs) {
    this.merchantConfigs = merchantConfigs;
  }
}
