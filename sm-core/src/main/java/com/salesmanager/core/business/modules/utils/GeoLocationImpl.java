package com.salesmanager.core.business.modules.utils;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.common.Addresss;
import com.salesmanager.core.modules.utils.GeoLocation;
import java.net.InetAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Using Geolite2 City database http://dev.maxmind.com/geoip/geoip2/geolite2/#Databases
 *
 * @author c.samson
 */
public class GeoLocationImpl implements GeoLocation {

  private DatabaseReader reader = null;
  private static final Logger LOGGER = LoggerFactory.getLogger(GeoLocationImpl.class);

  @Override
  public Addresss getAddress(String ipAddress) throws Exception {

    if (reader == null) {
      try {
        java.io.InputStream inputFile =
            GeoLocationImpl.class
                .getClassLoader()
                .getResourceAsStream("reference/GeoLite2-City.mmdb");
        reader = new DatabaseReader.Builder(inputFile).build();
      } catch (Exception e) {
        LOGGER.error("Cannot instantiate IP database", e);
      }
    }

    Addresss addresss = new Addresss();

    try {

      CityResponse response = reader.city(InetAddress.getByName(ipAddress));

      addresss.setCountry(response.getCountry().getIsoCode());
      addresss.setPostalCode(response.getPostal().getCode());
      addresss.setZone(response.getMostSpecificSubdivision().getIsoCode());
      addresss.setCity(response.getCity().getName());

    } catch (com.maxmind.geoip2.exception.AddressNotFoundException ne) {
      LOGGER.debug("Address not fount in DB " + ne.getMessage());
    } catch (Exception e) {
      throw new ServiceException(e);
    }

    return addresss;
  }
}
