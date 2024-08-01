package cloud.tianai.captcha.service.controller;

import cloud.tianai.captcha.application.ImageCaptchaApplication;
import cloud.tianai.captcha.application.vo.CaptchaResponse;
import cloud.tianai.captcha.application.vo.ImageCaptchaVO;
import cloud.tianai.captcha.common.constant.CaptchaTypeConstant;
import cloud.tianai.captcha.common.response.ApiResponse;
import cloud.tianai.captcha.service.util.Base64Util;
import cloud.tianai.captcha.spring.plugins.secondary.SecondaryVerificationApplication;
import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class CaptchaController {

    //private static final Logger logger = LoggerFactory.getLogger(CaptchaController.class);

    @Autowired
    private ImageCaptchaApplication imageCaptchaApplication;

    @RequestMapping("/gen")
    @ResponseBody
    public CaptchaResponse<ImageCaptchaVO> genCaptcha(HttpServletRequest request, @RequestParam(value = "type", required = false)String type) {
        if (StringUtils.isBlank(type)) {
            type = CaptchaTypeConstant.SLIDER;
        }
        if ("RANDOM".equals(type)) {
            int i = ThreadLocalRandom.current().nextInt(0, 4);
            if (i == 0) {
                type = CaptchaTypeConstant.SLIDER;
            } else if (i == 1) {
                type = CaptchaTypeConstant.CONCAT;
            } else if (i == 2) {
                type = CaptchaTypeConstant.ROTATE;
            } else{
                type = CaptchaTypeConstant.WORD_IMAGE_CLICK;
            }

        }
        CaptchaResponse<ImageCaptchaVO> response = imageCaptchaApplication.generateCaptcha(type);
        return response;
    }

    /*@CrossOrigin(origins = "*", methods = { RequestMethod.POST })
    @PostMapping("/check")
    @ResponseBody
    public ApiResponse<?> checkCaptcha(@RequestBody Data data,
                                       HttpServletRequest request) {
        ApiResponse<?> response = imageCaptchaApplication.matching(data.getId(), data.getData());
        if (response.isSuccess()) {
            return ApiResponse.ofSuccess(Collections.singletonMap("id", data.getId()));
        }
        return response;
    }*/
    @CrossOrigin(origins = "*", methods = { RequestMethod.POST })
    @PostMapping("/check")
    @ResponseBody
    public ApiResponse<?> checkCaptcha(@RequestBody Data data,
                                       HttpServletRequest request) {
        // 解码Base64编码的字符串
        String decodedData = Base64Util.decode(data.getData());
        //logger.info("Decoded data: {}", decodedData);

        // 将解码后的JSON字符串转换为ImageCaptchaTrack对象
        ObjectMapper objectMapper = new ObjectMapper();
        ImageCaptchaTrack imageCaptchaTrack;
        try {
            imageCaptchaTrack = objectMapper.readValue(decodedData, ImageCaptchaTrack.class);
            //logger.info("Parsed ImageCaptchaTrack: {}", imageCaptchaTrack);
        } catch (IOException e) {
            //logger.error("Failed to parse decoded data", e);
            return ApiResponse.ofError("Invalid data format");
        }

        // 进行验证码匹配
        ApiResponse<?> response = imageCaptchaApplication.matching(data.getId(), imageCaptchaTrack);
        if (response.isSuccess()) {
            return ApiResponse.ofSuccess(Collections.singletonMap("id", data.getId()));
        }
        return response;
    }

    /*@lombok.Data
    public static class Data {
        private String  id;
        private ImageCaptchaTrack data;
    }*/
    @lombok.Data
    public static class Data {
        private String id;
        private String data;  // 先接收Base64编码的字符串
    }


    /**
     * 二次验证，一般用于机器内部调用，这里为了方便测试
     * @param id id
     * @return boolean
     */
    @GetMapping("/check2")
    @ResponseBody
    public boolean check2Captcha(@RequestParam("id") String id) {
        // 如果开启了二次验证
        if (imageCaptchaApplication instanceof SecondaryVerificationApplication) {
            return ((SecondaryVerificationApplication) imageCaptchaApplication).secondaryVerification(id);
        }
        return false;
    }
}
