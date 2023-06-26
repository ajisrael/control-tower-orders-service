package control.tower.order.service;

import control.tower.core.config.XStreamConfig;
import control.tower.order.service.command.interceptors.CancelOrderCommandInterceptor;
import control.tower.order.service.command.interceptors.CreateOrderCommandInterceptor;
import control.tower.order.service.command.interceptors.RemoveOrderCommandInterceptor;
import control.tower.order.service.core.errorhandling.OrderServiceEventsErrorHandler;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

@EnableDiscoveryClient
@SpringBootApplication
@Import({ XStreamConfig.class })
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	@Autowired
	public void registerOrderCommandInterceptors(ApplicationContext context, CommandBus commandBus) {
		commandBus.registerDispatchInterceptor(
				context.getBean(CreateOrderCommandInterceptor.class)
		);
		commandBus.registerDispatchInterceptor(
				context.getBean(CancelOrderCommandInterceptor.class)
		);
		commandBus.registerDispatchInterceptor(
				context.getBean(RemoveOrderCommandInterceptor.class)
		);
	}

	@Autowired
	public void configure(EventProcessingConfigurer configurer) {
		configurer.registerListenerInvocationErrorHandler("order-group",
				configuration -> new OrderServiceEventsErrorHandler());
	}
}
