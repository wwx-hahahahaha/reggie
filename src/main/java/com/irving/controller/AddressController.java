package com.irving.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.irving.commonly.R;
import com.irving.domain.AddressBook;
import com.irving.domain.User;
import com.irving.service.AddressService;
import com.irving.utlis.ThreadLocalUtils;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.jdbc.Null;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@RequestMapping("addressBook")
@Controller
public class AddressController {
    private final AddressService addressService;

    @GetMapping("/list")
    @ResponseBody
    public R<?> list() {
        User user = (User) ThreadLocalUtils.getObjects();
        LambdaQueryWrapper<AddressBook>lm=new LambdaQueryWrapper<>();
        lm.eq(AddressBook::getUserId,user.getId());
        List<AddressBook> list = addressService.list(lm);
        return R.success(list);
    }

    @PutMapping("/default")
    @ResponseBody
    public R<?> defaults(@RequestBody AddressBook addressBook) {
        boolean b = addressService.updateDefault(addressBook);
        if (b) {
            return R.success("设置默认地址成功");
        }
        return R.error("设置默认地址失败");
    }

    @GetMapping("/default")
    @ResponseBody
    public R<?> getDefaults() {
        LambdaQueryWrapper<AddressBook> lm = new LambdaQueryWrapper<>();
        lm.eq(AddressBook::getIsDefault, "1");
        AddressBook one = addressService.getOne(lm);
        return R.success(one);
    }

    @PostMapping()
    @ResponseBody
    public R<?> save(@RequestBody AddressBook addressBook, HttpSession session) {
        User user = (User) session.getAttribute("user");
        addressBook.setUserId(user.getId());
        boolean save = addressService.save(addressBook);
        if (save) {
            return R.success("添加成功");
        }
        return R.error("添加失败");
    }

    @GetMapping("{id}")
    @ResponseBody
    public R<?> update(@PathVariable("id") Long id) {
        AddressBook byId = addressService.getById(id);
        return R.success(byId);
    }

    @PutMapping()
    @ResponseBody
    public R<?> putAddress(@RequestBody AddressBook addressBook) {
        boolean b = addressService.updateById(addressBook);
        if (b) {
            return R.success("修改成功");
        }
        return R.error("修改失败");
    }

    @DeleteMapping()
    @ResponseBody
    public R<?> delete(@RequestParam("ids") Long id) {
        boolean b = addressService.removeById(id);
        if (b) {
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }
}
