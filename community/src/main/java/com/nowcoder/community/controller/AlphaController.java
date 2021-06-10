package com.nowcoder.community.controller;

import com.nowcoder.community.service.AlphaService;
import com.nowcoder.community.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/alpha")
public class AlphaController {

    //注入service
    @Autowired
    private AlphaService alphaService;

    //浏览器路径
    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello() {
        return "Hello Spring Boot.";
    }

    //处理查询请求

    @RequestMapping("/data")
    @ResponseBody
    public String getData() {
        return alphaService.find();
    }

    //spring mvc中如何获得请求对象？响应对象
    //可以通过response对象向浏览器输出任何数据,所以没有返回值
    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response) {
        //读取请求中的数据
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());
        //请求头
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            String header = request.getHeader(name);
            System.out.println(name + ": " + header);
        }
        //请求体
        System.out.println(request.getParameter("code"));
        ///////////response返回响应数据
        //首先设置返回类型
        response.setContentType("text/html;charset=utf-8");
        //response输出流向浏览器输出
        try (
                PrintWriter writer = response.getWriter();
        ) {
            //writer打印网页
            writer.write("<h1>牛客网</h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Get请求，向服务器获取数据
    //查询所有学生，分页条件和限制
    // /students?current=1&limit=20
    @RequestMapping(path = "/students", method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(
            @RequestParam(name = "current", required = false, defaultValue = "1") int current,
            @RequestParam(name = "limit", required = false, defaultValue = "10")int limit) {
        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }
    //根据学生Id查询,参数是路径一部分的时候修改获取方式
    // /students/123
    @RequestMapping(path = "/students/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id) {
        System.out.println(id);
        return "a student";
    }

    //post 请求
    @RequestMapping(path = "/student", method = RequestMethod.POST)
    @ResponseBody
    //传入的参数就是表单中的数据
    public String saveStudent(String name, int age) {
        System.out.println(name);
        System.out.println(age);
        return "success saved student";
    }

    //响应动态html,想要返回html就不要加@ResponseBody
    @RequestMapping(path = "/teacher", method = RequestMethod.GET)
    public ModelAndView getTeacher() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("name", "张三");
        mav.addObject("age", "30");

        //设置模板路径和名字,template包的路径可以不用写,且后面不用写文件后缀名
        mav.setViewName("/demo/view");
        return mav;
    }

    //查询学校
    @RequestMapping(path = "/school", method = RequestMethod.GET)
    //返回类型写的string是返回的view的路径,Model是dispatchServlet自动实例化这个对象
    //在这个方法内传数据他也能得到,model数据撞到model里面,然后view的数据返回给dispatchServlet
    public String getSchool(Model model) {
        model.addAttribute("name", "北京大学");
        model.addAttribute("age", 80);
        return "/demo/view";
    }

    //除了响应html,还能响应JSON数据
    //通常在异步请求当中，比如注册B站，输入昵称，密码，光标切换就会判断昵称是否被占用，但是当前网页没有刷新
    //JAVA对象 --> JSON字符串 -->JS对象
    //sevlet调用这个方法的时候一看加了这个  @ResponseBody 注解， 返回的是Map类型，自动转换成JSON发给浏览器
    @RequestMapping(path = "/emp", method = RequestMethod.GET)
    //@ResponseBody加上返回字符串，不加返回HTML
    @ResponseBody
    public Map<String, Object> getEmp() {
        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "张三");
        emp.put("age", 23);
        emp.put("Salary", 8000);
        return emp;
    }

    @RequestMapping(path = "/emps", method = RequestMethod.GET)
    //@ResponseBody加上返回字符串，不加返回HTML
    @ResponseBody
    public List<Map<String, Object>> getEmps() {
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "张三");
        emp.put("age", 23);
        emp.put("Salary", 8000);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "李四");
        emp.put("age", 24);
        emp.put("Salary", 9000);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "王五");
        emp.put("age", 28);
        emp.put("Salary", 7000);
        list.add(emp);

        return list;
    }

    // cookie示例

    @RequestMapping(path = "/cookie/set", method = RequestMethod.GET)
    @ResponseBody //返回字符串简单测试一下
    public String setCookie(HttpServletResponse response) {
        //创建完cookie然后存到response里，在响应的时候才能自动携带给浏览器
        // 创建cookie，一个cookie只能存一对key-value
        Cookie cookie = new Cookie("code", CommunityUtil.generateUUID());
        // 设置cookie生效的范围,即访问哪些路径的时候需要发
        cookie.setPath("/community/alpha");
        // 存到内存里 默认关掉会消失 设置cookie的生存时间
        cookie.setMaxAge(60 * 10);
        // 发送cookie，放到response头里面
        response.addCookie(cookie);

        return "set cookie";
    }

    @RequestMapping(path = "/cookie/get", method = RequestMethod.GET)
    @ResponseBody
    public String getCookie(@CookieValue("code") String code) {
        System.out.println(code);
        return "get cookie";
    }

    // session示例
    @RequestMapping(path = "/session/set", method = RequestMethod.GET)
    @ResponseBody
    public String setSession(HttpSession session) {
        session.setAttribute("id", 1);
        session.setAttribute("name", "Test");
        return "set session";
    }

    @RequestMapping(path = "/session/get", method = RequestMethod.GET)
    @ResponseBody
    public String getSession(HttpSession session) {
        System.out.println(session.getAttribute("id"));
        System.out.println(session.getAttribute("name"));
        return "get session";
    }

    // ajax示例
    @RequestMapping(path = "/ajax", method = RequestMethod.POST)
    @ResponseBody
    public String testAjax(String name, int age) {
        System.out.println(name);
        System.out.println(age);
        return CommunityUtil.getJSONString(0, "操作成功!");
    }

}
