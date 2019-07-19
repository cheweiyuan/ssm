package com.qf.controller;

import com.qf.pojo.Item;
import com.qf.service.ItemService;
import com.qf.utils.PageInfo;
import com.qf.vo.ResultVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static com.qf.constant.SsmConstant.ITEM_ADD_UI;
import static com.qf.constant.SsmConstant.REDIRECT;

/**
 * cwy 2019/7/16 11:12
 **/
@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Value("${pic_types}")
    private String picTypes;

    //商品的首页
    @GetMapping("/list")
    public String list(String name,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "size", defaultValue = "5") Integer size,
                       Model model) {
        //1调用service查询数据
        PageInfo<Item> pageInfo = itemService.findItemByNameLikeAndLimit(name, page, size);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("name", name);

        return "item/item_list";
    }

    //跳转到添加商品页面
    @GetMapping("/add-ui")
    public String addUI(String addInfo, Model model) {

        model.addAttribute("addInfo", addInfo);
        return "item/item_add";
    }


    @Value("${pic_types}")
    public String picType;


    //添加商品信息
    //请求网址:http://localhost/item/add
    //请求方法:POST
    @PostMapping("/add")
    public String add(@Valid Item item, BindingResult bindingResult,
                      RedirectAttributes redirectAttributes,
                      MultipartFile picFile, HttpServletRequest req) throws IOException {

        //=====================校验参数======================
        if (bindingResult.hasErrors()) {
            //获得具体信息
            String msg = bindingResult.getFieldError().getDefaultMessage();

            redirectAttributes.addAttribute("addInfo", msg);
            return REDIRECT + ITEM_ADD_UI;

        }
        //===============================

        //1 对图片的大小做校验  要求图片小于5M
        long size = picFile.getSize();
        if (size > 542880L) {
            /*图片过大*/
            redirectAttributes.addAttribute("addInfo", "图片过大，要求小于5M");
            return REDIRECT + ITEM_ADD_UI;
        }

        boolean flag = false;

        //2 对图片的类型做校验   jpg,png,gif
        String[] types = picType.split(",");
        for (String type : types) {
            if (StringUtils.endsWithIgnoreCase(picFile.getOriginalFilename(), type)) {
                //图片类型正确
                flag = true;
                break;
            }
        }

        if (!flag) {
            //图片类型不正确
            redirectAttributes.addAttribute("addInfo", "图片类型不正确，要求" + picType);
            return REDIRECT + ITEM_ADD_UI;
        }

        //3 校验图片是否损坏
        BufferedImage image = ImageIO.read(picFile.getInputStream());
        if (image == null) {
            //图片已经损坏
            redirectAttributes.addAttribute("addInfo", "图片已经损坏");
            return REDIRECT + ITEM_ADD_UI;
        }

        //====================将图片保存到本地============================
        //1给图片起一个新名字
        String prefix = UUID.randomUUID().toString();
        String suffix = StringUtils.substringAfterLast(picFile.getOriginalFilename(), ".");
        String newName = prefix + "." + suffix;

        //2将图片保存到本地
        String path = req.getServletContext().getRealPath("") + "\\static\\img\\" + newName;
        File file = new File(path);
        //健壮性判断
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        picFile.transferTo(file);

        //3封装图片的访问路径
        String pic = "http://localhost/static/img/" + newName;

        //=====================保存商品信息 ==============================
        //将图片路径放入item
        item.setPic(pic);

        //调用service保存商品信息
        Integer count = itemService.save(item);
        //判断count
        if (count == 1) {
            return REDIRECT + "/item/list";
        } else {
            redirectAttributes.addAttribute("addInfo", "添加商品信息失败");
            return REDIRECT + ITEM_ADD_UI;
        }


    }

    //根据id删除商品信息
    @DeleteMapping("/del/{id}")
    @ResponseBody
    public ResultVO del(@PathVariable Long id) {
        //1 调用service删除
        Integer count = itemService.del(id);
        //2 根据结果给页面响应json
        if (count == 1) {
            //删除成功
            return new ResultVO(0, "成功", null);
        } else {
            return new ResultVO(88, "删除商品失败", null);
        }
    }

    //跳转到修改商品页面
    @GetMapping("/update-ui")
    public String updateUI( Integer id, Model model) {

        //1 调用service查询商品数据
        Item item = itemService.findById(id);
        model.addAttribute("item", item);
        model.addAttribute("id", id);

        //转发到修改页面
        return "item/item_update";
    }


    //修改商品信息
    //请求网址:http://localhost/item/update
    //请求方法:POST
    @PostMapping("/update")
    public String update( Item item,
                         HttpServletRequest Request,
                         Model model) throws IOException {
        //判断修改时 是否传递了新的图片
        MultipartFile picFile = item.getPicFile();
        if (picFile != null && picFile.getSize() > 0) {
            //上传了新的图片
            UploadPic uploadPic = new UploadPic(Request, model, picFile).invoke();
            if(uploadPic.is()) {
                return "item/item_update";
            }
            String pic = uploadPic.getPic();
            item.setPic(pic);
        }

            //2调用sercvice，修改商品信息
            Integer count = itemService.updateById(item);
        if (count == 1) {
            //跳转到展示页面
            return "redirect:/item/list";
        }

        return "redirect:/item/update/" + item.getId();

    }

    private class UploadPic {
        private boolean myResult;
        private HttpServletRequest req;
        private Model model;
        private MultipartFile picFile;
        private String pic;

        public UploadPic(HttpServletRequest req, Model model, MultipartFile picFile) {
            this.req = req;
            this.model = model;
            this.picFile = picFile;
        }

        boolean is() {
            return myResult;
        }

        public String getPic() {
            return pic;
        }

        public UploadPic invoke() throws IOException {
            //声明一个标识
            boolean flag = false;
            //  0 校验文件类型
            String[] types = picTypes.split(",");
            // 0.1 获取图片的原名字
            String filename = picFile.getOriginalFilename();
            for (String type : types) {
                if (StringUtils.endsWithIgnoreCase(filename, type)) {
                    //说明有匹配的类型 正确
                    flag = true;
                    break;
                }
            }
            //校验类型错误
            if (flag == false) {
                //图片类型错误
                model.addAttribute("picInfo", "图片类型错误，要求为" + picTypes);
                myResult = true;
                return this;
            }

            //0.5 校验图片是否损坏
            BufferedImage bufferedImage = ImageIO.read(picFile.getInputStream());
            if (bufferedImage == null) {
                //图片损坏
                model.addAttribute("picInfo", "图片已经损坏，请更换图片");
                myResult = true;
                return this;
            }
//            1.给图片重新起名字



            //1.2使用lang3下的StringUtils获取图片最后一个 后面的全部内容
            String typeName = StringUtils.substringAfterLast(filename, ".").toLowerCase();
            //1.3起个新名字
            String prefixName = UUID.randomUUID().toString();
            String newName = prefixName + "." + typeName;
            // 2 将图片保存到本地
            String path = req.getServletContext().getRealPath("") + "\\static\\img\\" + newName;
            File file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            picFile.transferTo(file);
            //3 封装一个访问路径
            pic = "http://localhost/static/img/" + newName;
            myResult = false;
            return this;
        }


    }


}
