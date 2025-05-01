package org.owpk.utils;

import java.util.Set;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

public class YamlObjectMapper implements Serializer {

	private final Yaml yaml;

	public YamlObjectMapper() {
		var options = new DumperOptions();
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		options.setPrettyFlow(true);

		var representer = new Representer(options) {
			@Override
			protected NodeTuple representJavaBeanProperty(Object javaBean, Property property,
					Object propertyValue, Tag customTag) {
				if (propertyValue == null) {
					return null;
				} else {
					return super.representJavaBeanProperty(javaBean, property, propertyValue, customTag);
				}
			}

			@Override
			protected MappingNode representJavaBean(Set<Property> properties, Object javaBean) {
				if (!classTags.containsKey(javaBean.getClass()))
					addClassTag(javaBean.getClass(), Tag.MAP);

				return super.representJavaBean(properties, javaBean);
			}
		};

		representer.getPropertyUtils()
				.setSkipMissingProperties(true);

		this.yaml = new Yaml(representer, options);
	}

	public <T> T convert(String yamlObject, Class<T> cl) {
		T object = yaml.loadAs(yamlObject, cl);
		if (object == null)
			throw new RuntimeException("Failed to load YAML properties: object is null");
		return object;
	}

	public String dumpToString(Object value) {
		var string = yaml.dumpAs(value, Tag.MAP, null);
		if (string == null)
			throw new RuntimeException("Cannot dump yaml object: " + value);
		return string;
	}

	@Override
	public <T> T convert(byte[] data, Class<T> clazz) {
		String yamlString = new String(data);
		return convert(yamlString, clazz);
	}

	@Override
	public <T> byte[] dump(T obj) {
		String yamlString = dumpToString(obj);
		return yamlString.getBytes();
	}
}