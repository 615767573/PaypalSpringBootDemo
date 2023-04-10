package com.example.paypalspringbootdemo.service.Impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.paypalspringbootdemo.domain.SmpAgentInfo;
import com.example.paypalspringbootdemo.mapper.SmpAgentInfoMapper;
import com.example.paypalspringbootdemo.service.ISmpAgentInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hsl
 * @since 2023-02-17
 */
@Service
@Slf4j
public class SmpAgentInfoServiceImpl extends ServiceImpl<SmpAgentInfoMapper, SmpAgentInfo> implements ISmpAgentInfoService {

    @Override
    public void query() {

//        Map<String, SmpAgentInfo> flist = new HashMap<>();
//
//        // 所有父级
//        List<SmpAgentInfo> agent_code = this.selectList(new EntityWrapper<SmpAgentInfo>().eq("parent_agent_code", ""));
//        log.info("size {}",agent_code.size());

        List<SmpAgentInfo> agent_code =new ArrayList<>();
        SmpAgentInfo smpAgentInfo =new SmpAgentInfo();
        smpAgentInfo.setAgentCode("kangjia");
        agent_code.add(smpAgentInfo);
        agent_code.forEach(agent_codes -> {
            agent_codes.setGrade(0);
            List fi = new ArrayList<>();
            Map map = new HashMap();
            map.put(agent_codes.getAgentCode(),agent_codes.getGrade());
            fi.add(map);
            // 一级代理商
            List smpAgentInfos1 = queryAllAgentCode(agent_codes.getAgentCode(), fi,0);
            log.info(String.valueOf(smpAgentInfos1));
            });
    }



    public Map queryOneAgentCode(String agentCodes, Map agentCodeList,boolean digiu) {



        if(digiu){

            Map a = new HashMap();
            // 下面的代理商
            List<SmpAgentInfo> OneAgentCode = this.selectList(new EntityWrapper<SmpAgentInfo>().eq("parent_agent_code", agentCodes));
            if(CollectionUtils.isEmpty(OneAgentCode)){
                return null;
            }else {
                a.put(agentCodes,OneAgentCode);
                OneAgentCode.forEach(item->{
                    queryOneAgentCode(item.getAgentCode(), agentCodeList,true);
                });
                return a;
            }

        }else {
            // 下面的代理商
            List<SmpAgentInfo> OneAgentCode = this.selectList(new EntityWrapper<SmpAgentInfo>().eq("parent_agent_code",agentCodes ));
            if(CollectionUtils.isEmpty(OneAgentCode)){
                agentCodeList.put(agentCodes,null);
                return agentCodeList;
            }else {

                List<Map<String, Object>> testList = new ArrayList<>();
                OneAgentCode.forEach(item->{
                    queryOneAgentCode(item.getAgentCode(), agentCodeList, true);
                });

                agentCodeList.put(agentCodes,testList);
            }

            return agentCodeList;

        }






    }


    public List<Map> queryAllAgentCode(String agentCode, List<Map> agentCodeList, int i)
    {

        List<SmpAgentInfo> agentList =
                baseMapper.selectList(new EntityWrapper<SmpAgentInfo>().eq("parent_agent_code", agentCode));
        if(!CollectionUtils.isEmpty(agentList)){
            i++;
        }

        for (SmpAgentInfo curCode : agentList)
        {
            curCode.setGrade(i);
            Map agent = new HashMap();
            agent.put(curCode.getAgentCode(),curCode.getGrade());
            agentCodeList.add(agent);
            queryAllAgentCode(curCode.getAgentCode(), agentCodeList,i);
        }
        return agentCodeList;
    }


}
