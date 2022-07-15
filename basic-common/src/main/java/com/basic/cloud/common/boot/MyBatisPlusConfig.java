package com.basic.cloud.common.boot;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.basic.cloud.common.base.IdInjectionStrategy;
import com.basic.cloud.common.base.TenantTableFilterConfigManager;
import com.basic.cloud.common.base.TenantTableFilterProvider;
import com.basic.cloud.common.contstant.OrgConst;
import com.basic.cloud.common.utils.AppContextHelper;
import com.basic.cloud.common.utils.UserUtil;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author lanrenspace@163.com
 * @Description: mp 配置
 **/
@Configuration
public class MyBatisPlusConfig {

    @Autowired(required = false)
    private List<TenantTableFilterProvider> tenantTableFilterProviders;

    /**
     * mp interceptor
     *
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor plusInterceptor = new MybatisPlusInterceptor();
        plusInterceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {
            @Override
            public Expression getTenantId() {
                return new StringValue(UserUtil.getUser().getTenantCode());
            }

            @Override
            public String getTenantIdColumn() {
                return OrgConst.TENANT_CODE_COLUMN;
            }

            @Override
            public boolean ignoreTable(String tableName) {
                return !tenantTableFilterConfigManager().config().contains(tableName);
            }
        }));
        plusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return plusInterceptor;
    }

    /**
     * 避免mybatis的一级、二级缓存出现问题
     *
     * @return
     */
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            configuration.setUseDeprecatedExecutor(false);
        };
    }

    /**
     * mp idKey
     *
     * @return
     */
    @Bean
    public IKeyGenerator keyGenerator() {
        return incrementerName -> AppContextHelper.getBean(IdInjectionStrategy.class).id();
    }

    /**
     * 管理器实现
     *
     * @return
     */
    @Bean
    public TenantTableFilterConfigManager tenantTableFilterConfigManager() {
        return () -> {
            Set<String> filterTables = new HashSet<>();
            if (!CollectionUtils.isEmpty(tenantTableFilterProviders)) {
                tenantTableFilterProviders.forEach(tenantTableFilterProvider -> {
                    Set<String> tables = tenantTableFilterProvider.configTable();
                    if (null != tables && tables.size() > 0) {
                        filterTables.addAll(tables);
                    }
                });
            }
            return filterTables;
        };
    }
}
