package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.core.system.SystemConfig;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.wx.dto.PickTimeDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/wx/mall")
@RestController
public class WxMallController {

    @GetMapping("")
    public Object detail(){
        Map<String, Object> detail = new HashMap<>();
        detail.put("name", SystemConfig.getMallName());
        detail.put("address", SystemConfig.getMallAddress());
        detail.put("phone", SystemConfig.getMallPhone());
        detail.put("qq", SystemConfig.getMallQQ());
        detail.put("longitude", SystemConfig.getMallLongitude());
        detail.put("latitude", SystemConfig.getMallLatitude());
        detail.put("banner",SystemConfig.getMallBanner());
        detail.put("logo",SystemConfig.getMallLogo());
        detail.put("desc",SystemConfig.getMallDesc());
        detail.put("notice",SystemConfig.getMallNotice());
        detail.put("businessStartTime",SystemConfig.getBusinessStartTime());
        detail.put("businessEndTime",SystemConfig.getBusinessEndTime());
        return ResponseUtil.ok(detail);
    }

    /**
     * 自取时间
     * @return
     */
    @GetMapping("pickupTime")
    public Object pickupTimeList(){
        String time = SystemConfig.getPickTimes();
        String[] timeList = time.split(",");
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime tomorrow = today.plusDays(1);
        DateTimeFormatter f1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter f2 = DateTimeFormatter.ofPattern("MM-dd");
        DateTimeFormatter f3 = DateTimeFormatter.ofPattern("HH:mm");
        String todayDate = today.format(f1);
        String simpleTodayDate = today.format(f2);
        String tomorrowDate = tomorrow.format(f1);
        String simpleTomorrowDate = tomorrow.format(f2);
        String todayTime = today.format(f3);

        PickTimeDto todayDto = new PickTimeDto();
        todayDto.setText("今天（" + simpleTodayDate + "）");
        todayDto.setValue(todayDate);
        todayDto.setId(1);
        ArrayList<PickTimeDto> todayChildren = new ArrayList<>();

        PickTimeDto tomorrowDto = new PickTimeDto();
        tomorrowDto.setText("明天（" + simpleTomorrowDate + "）");
        tomorrowDto.setValue(tomorrowDate);
        tomorrowDto.setId(2);
        ArrayList<PickTimeDto> tomorrowChildren = new ArrayList<>();

        Integer id = 3;
        for (String s : timeList) {
            if(s.compareTo(todayTime) > 0){
                PickTimeDto dto1 = new PickTimeDto();
                dto1.setText(s);
                dto1.setValue(todayDate + " " + s + ":00");
                dto1.setId(id);
                todayChildren.add(dto1);
                id++;
            }

            PickTimeDto dto2 = new PickTimeDto();
            dto2.setText(s);
            dto2.setValue(tomorrowDate + " " + s + ":00");
            dto2.setId(id);
            tomorrowChildren.add(dto2);
            id++;
        }

        todayDto.setChildren(todayChildren);
        tomorrowDto.setChildren(tomorrowChildren);

        ArrayList<PickTimeDto> data = new ArrayList<>();
        data.add(todayDto);
        data.add(tomorrowDto);
        return ResponseUtil.ok(data);
    }
}
