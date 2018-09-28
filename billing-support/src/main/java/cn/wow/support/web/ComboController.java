package cn.wow.support.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.wow.common.domain.Combo;
import cn.wow.common.service.ComboService;
import cn.wow.common.utils.AjaxVO;
import cn.wow.common.utils.pagination.PageMap;

@Controller
@RequestMapping(value = "combo")
public class ComboController extends AbstractController{

    private static Logger logger = LoggerFactory.getLogger(ComboController.class);

    @Autowired
    private ComboService comboService;

    @RequestMapping(value = "/list")
    public String list(HttpServletRequest httpServletRequest, Model model, String name, String startCreateTime,
			String endCreateTime) {
       
    	Map<String, Object> map = new PageMap(httpServletRequest);
		map.put("custom_order_sql", "name asc");
		map.put("isDelete", "0");
		
		if (StringUtils.isNotBlank(name)) {
			map.put("name", name);
			model.addAttribute("name", name);
		}
		if (StringUtils.isNotBlank(startCreateTime)) {
			map.put("startCreateTime", startCreateTime + " 00:00:00");
			model.addAttribute("startCreateTime", startCreateTime);
		}
		if (StringUtils.isNotBlank(endCreateTime)) {
			map.put("endCreateTime", endCreateTime + " 23:59:59");
			model.addAttribute("endCreateTime", endCreateTime);
		}
        List<Combo> dataList = comboService.selectAllList(map);

        model.addAttribute("dataList", dataList);
        return "combo/combo_list";
    }

    @RequestMapping(value = "/detail")
    public String detail(HttpServletRequest request, Model model, String id){
        if(StringUtils.isNotBlank(id)){
            Combo combo = comboService.selectOne(Long.parseLong(id));
            model.addAttribute("facadeBean", combo);
        }
        return "combo/combo_detail";
    }

    @ResponseBody
    @RequestMapping(value = "/save")
    public AjaxVO save(HttpServletRequest request, Model model, String id, String comboName, String remark, Double price, Integer duration, Date createTime){
        Combo combo = null;
        AjaxVO vo = new AjaxVO();
		vo.setSuccess(true);
		
        try{
            if(StringUtils.isNotBlank(id)){
                combo = comboService.selectOne(Long.parseLong(id));
                combo.setName(comboName);
                combo.setRemark(remark);
                combo.setPrice(price);
                combo.setDuration(duration);
                comboService.update(getCurrentUserName(), combo);
                
                vo.setMsg("编辑成功");
            }else{
            	Map<String, Object> rMap = new HashMap<String, Object>();
				rMap.put("name", comboName);
				List<Combo> userList = comboService.selectAllList(rMap);
				
				if (userList != null && userList.size() > 0) {
					vo.setData("userName");
					vo.setMsg("套餐名称已经存在");
					vo.setSuccess(false);
					return vo;
				} else {
					combo = new Combo();
	                combo.setName(comboName);
	                combo.setRemark(remark);
	                combo.setPrice(price);
	                combo.setDuration(duration);
	                combo.setIsDelete(0);
	                combo.setCreateTime(new Date());
	                comboService.save(getCurrentUserName(), combo);
	                
	                vo.setMsg("新增成功");
				}
            }
        }catch(Exception ex){
        	logger.error("套餐保存失败", ex);
			vo.setMsg("保存失败，系统异常");
			vo.setSuccess(false);
			return vo;
        }
        
        return vo;
    }

	@ResponseBody
	@RequestMapping(value = "/delete")
	public AjaxVO delete(HttpServletRequest request, String id) {
		AjaxVO vo = new AjaxVO();
		vo.setMsg("删除成功");

		try {
			Combo combo = comboService.selectOne(Long.parseLong(id));

			if (combo != null) {
				comboService.deleteByPrimaryKey(getCurrentUserName(), combo);
			} else {
				vo.setMsg("删除失败，记录不存在");
				vo.setSuccess(false);
				return vo;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("套餐删除失败", ex);

			vo.setMsg("删除失败，系统异常");
			vo.setSuccess(false);
			return vo;
		}

		return vo;
	}
}