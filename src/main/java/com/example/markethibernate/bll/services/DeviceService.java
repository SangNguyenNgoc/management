package com.example.markethibernate.bll.services;

import com.example.markethibernate.bll.dtos.DeviceValidator;
import com.example.markethibernate.dal.dao.DeviceDao;
import com.example.markethibernate.dal.entities.DeviceEntity;
import com.example.markethibernate.dal.entities.PersonEntity;
import com.example.markethibernate.utils.AppUtil;
import com.example.markethibernate.utils.ValidatorUtil;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class DeviceService {

    private static final Logger logger = Logger.getLogger(DeviceService.class.getName());

    private DeviceService() {
    }

    public static DeviceService getInstance() {
        return DeviceServiceHolder.INSTANCE;
    }

    public List<DeviceEntity> getAll() {
        return DeviceDao.getInstance().findAll();
    }

    public List<DeviceEntity> getAllAndUsage(String type) {
        List<DeviceEntity> deviceEntities = DeviceDao.getInstance().findAllAndUsage();
        if (type == null) {
            return deviceEntities;
        }
        return deviceEntities.stream()
                .filter(item -> item.getId().toString().startsWith(type))
                .collect(Collectors.toList());
    }

    public DeviceEntity getById(String idString) {
        Long id = AppUtil.parseId(idString);
        if (id == null) {
            return null;
        }
        return DeviceDao.getInstance().findById(id);
    }

    public List<DeviceEntity> getByType(String type) {
        Long t = AppUtil.parseId(type);
        if (t == null) {
            return null;
        }
        return DeviceDao.getInstance().findByType(t);
    }

    public boolean checkUse(DeviceEntity device) {
        return device.getUsageInfos().stream().anyMatch(item -> item.getReturnTime() == null);
    }

    public DeviceEntity createDevice(String idString, String name, String description, boolean status) {
        if(!checkId(idString).isBlank()) {
            return null;
        }
        if (!checkName(name).isBlank()) {
            return null;
        }
        if (!checkDescription(description).isBlank()) {
            return null;
        }
        DeviceEntity device = new DeviceEntity();
        device.setId(Long.parseLong(idString));
        device.setName(name);
        device.setDescription(description);
        device.setStatus(status);
        DeviceDao.getInstance().createDevice(device);
        return device;
    }

    public boolean deleteDeviceById(String idString) {
        DeviceEntity device = getById(idString);
        if (device == null) {
            return false;
        }
        return DeviceDao.getInstance().deleteDeviceById(AppUtil.parseId(idString));
    }

    public int deleteDeviceByType(String type) {
        Long t = AppUtil.parseId(type);
        if (t == null) {
            return 0;
        }
        return DeviceDao.getInstance().deleteDeviceByType(t);
    }

    public DeviceEntity updateDevice(String idString, String name, String description) {
        DeviceEntity device = getById(idString);
        if (device == null) {
            return null;
        }
        if (!checkName(name).isBlank()) {
            return null;
        }
        if (!checkDescription(description).isBlank()) {
            return null;
        }
        device.setName(name);
        device.setDescription(description);
        DeviceDao.getInstance().updateDevice(device);
        return device;
    }

    public String checkId(String id) {
        String validate = ValidatorUtil.validateField(
                DeviceValidator.builder()
                        .id(id)
                        .build(),
                "id"
        );
        if(validate.isBlank()) {
            validate += checkIdTaken(id);
        }
        return validate;
    }

    public String checkIdTaken(String id) {
        if(getById(id) != null) {
            return "Mã thiết bị trùng khớp";
        }
        return "";
    }

    public String checkName(String name) {
        return ValidatorUtil.validateField(
                DeviceValidator.builder()
                        .name(name)
                        .build(),
                "name"
        );
    }

    public String checkDescription(String description) {
        return ValidatorUtil.validateField(
                DeviceValidator.builder()
                        .description(description)
                        .build(),
                "description");
    }

    public String saveFromExcel(File selectedFile) {
        try (Workbook workbook = WorkbookFactory.create(selectedFile)) {
            Sheet sheet = workbook.getSheetAt(1);
            StringBuilder result = new StringBuilder();
            for (Row row : sheet) {
                if(row.getCell(0) == null) {
                    break;
                }
                if (row.getRowNum() > 0) {
                    DeviceEntity device = createDevice(
                            String.valueOf((long) row.getCell(0).getNumericCellValue()),
                            row.getCell(1).getStringCellValue(),
                            row.getCell(2).getStringCellValue(),
                            true
                    );
                    if (device == null) {
                        result.append(row.getRowNum()).append(", ");
                    }
                }
            }
            return String.valueOf(result);
        } catch (IOException e) {
            return null;
        }
    }


    private static class DeviceServiceHolder {
        private static final DeviceService INSTANCE = new DeviceService();
    }

}
