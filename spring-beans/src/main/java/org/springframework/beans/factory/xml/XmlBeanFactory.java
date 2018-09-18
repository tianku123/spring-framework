/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.beans.factory.xml;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.io.Resource;

/**
 * Convenience extension of {@link DefaultListableBeanFactory} that reads bean definitions
 * from an XML document. Delegates to {@link XmlBeanDefinitionReader} underneath; effectively
 * equivalent to using an XmlBeanDefinitionReader with a DefaultListableBeanFactory.
 *
 * <p>The structure, element and attribute names of the required XML document
 * are hard-coded in this class. (Of course a transform could be run if necessary
 * to produce this format). "beans" doesn't need to be the root element of the XML
 * document: This class will parse all bean definition elements in the XML file.
 *
 * <p>This class registers each bean definition with the {@link DefaultListableBeanFactory}
 * superclass, and relies on the latter's implementation of the {@link BeanFactory} interface.
 * It supports singletons, prototypes, and references to either of these kinds of bean.
 * See {@code "spring-beans-3.x.xsd"} (or historically, {@code "spring-beans-2.0.dtd"}) for
 * details on options and configuration style.
 *
 * <p><b>For advanced needs, consider using a {@link DefaultListableBeanFactory} with
 * an {@link XmlBeanDefinitionReader}.</b> The latter allows for reading from multiple XML
 * resources and is highly configurable in its actual XML parsing behavior.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Chris Beams
 * @since 15 April 2001
 * @see org.springframework.beans.factory.support.DefaultListableBeanFactory
 * @see XmlBeanDefinitionReader
 * @deprecated as of Spring 3.1 in favor of {@link DefaultListableBeanFactory} and
 * {@link XmlBeanDefinitionReader}
 */

/**
 * 在Spring中，实际上是把 DefaultListableBeanFactory 作为一个默认的功能完整的IoC容器.
 * XmlBeanFactory 在继承了 DefaultListableBeanFactory 容器的功能的同时，增加了新的功能，
 * 它是一个可以读取以XML文件方式定义的 BeanDefinition 的 IoC容器。
 * 对这些 XML文件定义信息的处理并不是有 XmlBeanFactory直接完成的。在 XmlBeanFactory中，
 * 初始化了一个 XmlBeanDefinitionReader 对象，有了这个 Reader 对象，那些以 XML方式定义的
 * BeanDefinition就有了处理的地方。
 */
@Deprecated
@SuppressWarnings({"serial", "all"})
public class XmlBeanFactory extends DefaultListableBeanFactory {

	private final XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(this);


	/**
	 * Create a new XmlBeanFactory with the given resource,
	 * which must be parsable using DOM.
	 * @param resource XML resource to load bean definitions from
	 * @throws BeansException in case of loading or parsing errors
	 */
	/**
	 *
	 * 构造 XmlBeanFactory这个 IoC容器时，需要指定 BeanDefinition的信息来源，而这个信息来源需要封装成Spring中的Resource类来给出。
	 * Resource 是Spring用来封装I/O操作的类。
	 * 比如，BeanDefinition信息是以XML文件形式存在的，那么可以使用像“ClassPathResource res = new ClassPathResource（"bean.xml"）”
	 * 这样具体的 ClassPathResource来构造需要的Resource，然后将 Resource作为构造参数传递给 XmlBeanFactory构造函数。
	 * 这样，IoC容器就可以方便地定位到需要的BeanDefinition信息来对Bean完成容器的初始化和依赖注入过程。
	 *
	 * @param resource
	 * @throws BeansException
	 */
	public XmlBeanFactory(Resource resource) throws BeansException {
		this(resource, null);
	}

	/**
	 * Create a new XmlBeanFactory with the given input stream,
	 * which must be parsable using DOM.
	 * @param resource XML resource to load bean definitions from
	 * @param parentBeanFactory parent bean factory
	 * @throws BeansException in case of loading or parsing errors
	 */
	public XmlBeanFactory(Resource resource, BeanFactory parentBeanFactory) throws BeansException {
		super(parentBeanFactory);
		/**
		 * 调用启动从 Resource中载入 BeanDefinition的过程，loadBeanDefinitions同时也是Ioc容器初始化的重要组成部分。
		 */
		this.reader.loadBeanDefinitions(resource);
	}

}
