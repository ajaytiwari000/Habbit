package com.salesmanager.core.business.services.catalog.attribute.image;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.cms.attributes.Pack.PackFileManager;
import com.salesmanager.core.business.repositories.catalog.attribute.image.PackIconRepository;
import com.salesmanager.core.business.services.catalog.attribute.PackService;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.product.Pack;
import com.salesmanager.core.model.catalog.product.PackIcon;
import com.salesmanager.core.model.content.FileContentType;
import com.salesmanager.core.model.content.ImageContentFile;
import com.salesmanager.core.model.content.OutputContentFile;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

@Service("packIcon")
public class PackIconServiceImpl extends SalesManagerEntityServiceImpl<Long, PackIcon>
    implements PackIconService {
  private static final Logger LOGGER = LoggerFactory.getLogger(PackIconServiceImpl.class);
  @Inject private PackFileManager packFileManager;
  @Inject private PackService packService;
  @Inject private PackIconService packIconService;

  private PackIconRepository packIconRepository;

  @Inject
  public PackIconServiceImpl(PackIconRepository packIconRepository) {
    super(packIconRepository);
    this.packIconRepository = packIconRepository;
  }

  @Override
  public PackIcon getById(Long id) {
    return packIconRepository.findById(id).orElse(null);
  }

  @Override
  public void addPackIcons(Pack pack, List<PackIcon> packIcons) throws ServiceException {}

  @Override
  public void addPackIcon(Long id, MultipartFile[] files) throws ServiceException {
    try {
      Pack pack = packService.getById(id);
      if (Objects.nonNull(pack.getPackIcon())) {
        throw new ServiceException("Pack Icon Already exists.");
      }
      PackIcon packIcon = new PackIcon();
      for (MultipartFile multipartFile : files) {
        if (!multipartFile.isEmpty()) {
          packIcon.setImage(multipartFile.getInputStream());
          packIcon.setIcon(multipartFile.getOriginalFilename());
        }
      }
      Assert.notNull(packIcon.getImage());
      InputStream inputStream = packIcon.getImage();
      ImageContentFile cmsContentImage = new ImageContentFile();
      cmsContentImage.setFileName(packIcon.getIcon());
      cmsContentImage.setFile(inputStream);
      cmsContentImage.setFileContentType(FileContentType.PACK);
      addPackIcon(pack, packIcon, cmsContentImage);
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  @Override
  public void addPackIcon(Pack pack, PackIcon packIcon, ImageContentFile inputImage)
      throws ServiceException {
    try {
      Assert.notNull(inputImage.getFile(), "ImageContentFile.file cannot be null");
      packIcon.setPackValue(pack.getPackSizeValue());
      packFileManager.addImage(packIcon, inputImage);
      pack.setPackIcon(this.create(packIcon));
      packService.update(pack);
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(e);
    } finally {
      try {
        if (inputImage.getFile() != null) {
          inputImage.getFile().close();
        }
      } catch (Exception ignore) {
      }
    }
  }

  @Override
  public void removePackIcon(PackIcon packIcon, Long packId) throws ServiceException {
    try {
      Pack pack = packService.getById(packId);
      if (pack != null) {
        packIcon.setPackValue(pack.getPackSizeValue());
        packFileManager.removeImage(packIcon);
        packIconService.delete(packIcon);
        pack.setPackIcon(null);
        packService.update(pack);
      }
    } catch (Exception e) {
      LOGGER.info("Error while removing the pack image", e);
      throw new ServiceException(e);
    }
  }

  @Override
  public PackIcon saveOrUpdate(PackIcon packIcon) throws ServiceException {
    return null;
  }

  @Override
  public List<OutputContentFile> getPackIcons(Pack pack) throws ServiceException {
    return null;
  }

  @Override
  public OutputContentFile getPackIcon(Pack pack) throws ServiceException {
    return null;
  }

  @Override
  public OutputContentFile getPackIcon(String packSize) throws ServiceException {
    return null;
  }

  @Override
  public OutputContentFile getPackIcon(PackIcon packIcon) throws ServiceException {
    return null;
  }

  @Override
  public PackIcon getPackImageByPackId(Long id) throws ServiceException {
    return packService.getById(id).getPackIcon();
  }
}
