package cn.wow.support.web;

import java.util.Date;
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

import cn.wow.common.domain.Scale;
import cn.wow.common.service.ScaleService;
import cn.wow.common.utils.AjaxVO;
import cn.wow.common.utils.pagination.PageMap;

@Controller
@RequestMapping(value = "scale")
public class ScaleController extends AbstractController{

    private static Logger logger = LoggerFactory.getLogger(ScaleController.class);

    @Autowired
    private ScaleService scaleService;

    @RequestMapping(value = "/list")
    public String list(HttpServletRequest httpServletRequest, Model model, String startCreateTime, String endCreateTime) {
       
    	Map<String, Object> map = new PageMap(httpServletRequest);
		map.put("custom_order_sql", "val asc");
		map.put("isDelete", "0");
		
		if (StringUtils.isNotBlank(startCreateTime)) {
			map.put("startCreateTime", startCreateTime + " 00:00:00");
			model.addAttribute("startCreateTime", startCreateTime);
		}
		if (StringUtils.isNotBlank(endCreateTime)) {
			map.put("endCreateTime", endCreateTime + " 23:59:59");
			model.addAttribute("endCreateTime", endCreateTime);
		}
        List<Scale> dataList = scaleService.selectAllList(map);

        model.addAttribute("dataList", dataList);
        return "scale/scale_list";
    }

    @RequestMapping(value = "/detail")
    public String detail(HttpServletRequest request, Model model, String id){
        if(StringUtils.isNotBlank(id)){
            Scale scale = scaleService.selectOne(Long.parseLong(id));
            model.addAttribute("facadeBean", scale);
        }
        return "scale/scale_detail";
    }

    @ResponseBody
    @RequestMapping(value = "/save")
    public AjaxVO save(HttpServletRequest request, Model model, String id, String val, String remark){
        Scale scale = null;
        AjaxVO vo = new AjaxVO();
		vo.setSuccess(true);
		
        try{
            if(StringUtils.isNotBlank(id)){
                scale = scaleService.selectOne(Long.parseLong(id));
                if(StringUtils.isNotBlank(val)) {
                	 scale.setVal(Integer.parseInt(val));
                }
                scale.setRemark(remark);
                scaleService.update(getCurrentUserName(), scale);
                
                vo.setMsg("编辑成功");
            }else{
				scale = new Scale();
				if(StringUtils.isNotBlank(val)) {
                	 scale.setVal(Integer.parseInt(val));
                }
                scale.setRemark(remark);
                scale.setIsDelete(0);
                scale.setCreateTime(new Date());
                scaleService.save(getCurrentUserName(), scale);
                
                vo.setMsg("新增成功");
        }
        }catch(Exception ex){
        	logger.error("保存失败", ex);
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
			Scale scale = scaleService.selectOne(Long.parseLong(id));

			if (scale != null) {
				scaleService.deleteByPrimaryKey(getCurrentUserName(), scale);
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