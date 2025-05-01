package org.owpk.utils.serializers;

import java.util.Set;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import lombok.Getter;

@Getter
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

	public <T> T convert(String yamlObject, Class<T> cl) throws SerializingException {
		T object = yaml.loadAs(yamlObject, cl);
		if (object == null)
			throw new SerializingException("Failed to load YAML properties: object is null");
		return object;
	}

	public String dumpToString(Object value) throws SerializingException {
		var string = yaml.dumpAs(value, Tag.MAP, null);
		if (string == null)
			throw new RuntimeException("Cannot dump yaml object: " + value);
		return string;
	}

	@Override
	public <T> T convert(byte[] data, Class<T> clazz) throws SerializingException {
		String yamlString = new String(data);
		return convert(yamlString, clazz);
	}

	@Override
	public <T> byte[] dump(T obj) throws SerializingException {
		String yamlString = dumpToString(obj);
		return yamlString.getBytes();
	}
}