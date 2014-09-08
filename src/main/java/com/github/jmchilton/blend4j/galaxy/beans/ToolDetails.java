package com.github.jmchilton.blend4j.galaxy.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;

/**
 * Galaxy tool details.
 * 
 * @author Franklin Bristow <franklin.bristow@phac-aspc.gc.ca>
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ToolDetails extends GalaxyObject {

	private String description;

	private String name;

	private List<Map<String, String>> outputs;

	private String version;

	private List<ToolDetailsInputs> inputs;

	@JsonProperty("inputs")
	protected void setInputs(final List<ToolDetailsInputs> inputs) {
		this.inputs = inputs;
	}

	public List<ToolDetailsInputs> getInputs() {
		return this.inputs;
	}

	@JsonProperty("version")
	protected void setVersion(final String version) {
		this.version = version;
	}

	public String getVersion() {
		return this.version;
	}

	@JsonProperty("name")
	protected void setName(final String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	@JsonProperty("outputs")
	protected void setOutputs(final List<Map<String, String>> outputs) {
		this.outputs = outputs;
	}

	public List<Map<String, String>> getOutputs() {
		return this.outputs;
	}

	@JsonProperty("description")
	protected void setDescription(final String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	/**
	 * Generic super-class for all types of inputs relating to a tool. Uses
	 * {@link @JsonSubTypes} and {@link JsonTypeInfo} to instruct Jackson on how
	 * to determine which subclass to use when parsing a JSON document based on
	 * the 'type' option.
	 * 
	 * @author Franklin Bristow <franklin.bristow@phac-aspc.gc.ca>
	 *
	 */
	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
	@JsonSubTypes({
			@JsonSubTypes.Type(value = ToolDetailsDataInputs.class, name = "data"),
			@JsonSubTypes.Type(value = ToolDetailsConditionalInputs.class, name = "conditional"),
			@JsonSubTypes.Type(value = ToolDetailsSelectInputs.class, name = "select"),
			@JsonSubTypes.Type(value = ToolDetailsTextInputs.class, name = "text") })
	public static class ToolDetailsInputs {
		private String name;
		private String help;
		private String label;

		@JsonProperty("help")
		protected void setHelp(final String help) {
			this.help = help;
		}

		public String getHelp() {
			return this.help;
		}

		@JsonProperty("label")
		protected void setLabel(final String label) {
			this.label = label;
		}

		public String getLabel() {
			return this.label;
		}

		@JsonProperty("name")
		protected void setName(final String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}
	}

	/**
	 * Data inputs to a tool have no additional properties that I am interested
	 * in, but should still be parsed out.
	 * 
	 * @author Franklin Bristow <franklin.bristow@phac-aspc.gc.ca>
	 *
	 */
	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonTypeName("data")
	public static class ToolDetailsDataInputs extends ToolDetailsInputs {
	}

	/**
	 * This is a type of input that changes the display based on a selection
	 * that the user has made. The value selected in
	 * {@link ToolDetailsConditionalInputs#testParameter} causes the Galaxy
	 * display to display different sets of inputs. The value set of
	 * {@link ToolDetailsConditionalInputs#testParameter} is used as the set of
	 * keys for {@link ToolDetailsConditionalInputs#cases}. The value set of
	 * {@link ToolDetailsConditionalInputs#cases} itself, then, is a further
	 * complete list of inputs.
	 * 
	 * @author Franklin Bristow <franklin.bristow@phac-aspc.gc.ca>
	 *
	 */
	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonTypeName("conditional")
	public static class ToolDetailsConditionalInputs extends ToolDetailsInputs {
		private ToolDetailsSelectInputs testParameter;
		private Map<String, List<ToolDetailsInputs>> cases = new HashMap<String, List<ToolDetailsInputs>>();

		@JsonProperty("cases")
		protected void setCases(final List<ConditionalWhen> whens) {
			for (ConditionalWhen when : whens) {
				cases.put(when.value, when.inputs);
			}
		}

		public Map<String, List<ToolDetailsInputs>> getCases() {
			return this.cases;
		}

		@JsonProperty("test_param")
		protected void setTestParameter(
				final ToolDetailsSelectInputs testParameter) {
			this.testParameter = testParameter;
		}

		public ToolDetailsSelectInputs getTestParameter() {
			return this.testParameter;
		}

		@Override
		public String getHelp() {
			return this.testParameter.getHelp();
		}

		@Override
		public String getLabel() {
			return this.testParameter.getLabel();
		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		private static class ConditionalWhen {
			@JsonProperty("inputs")
			private List<ToolDetailsInputs> inputs;

			@JsonProperty("value")
			private String value;
		}
	}

	/**
	 * A simple text input.
	 * 
	 * @author Franklin Bristow <franklin.bristow@phac-aspc.gc.ca>
	 *
	 */
	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonTypeName("text")
	public static class ToolDetailsTextInputs extends ToolDetailsInputs {
		private String value;

		@JsonProperty("value")
		protected void setValue(final String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}

	/**
	 * A combo-box or radio button style selection input, where the user can
	 * choose from a set of options. {@link ToolDetailsSelectInputs#options} is
	 * a key-value pair of the internal Galaxy identifier for the value and the
	 * actual labels shown on the screen.
	 * 
	 * @author Franklin Bristow <franklin.bristow@phac-aspc.gc.ca>
	 *
	 */
	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonTypeName("select")
	public static class ToolDetailsSelectInputs extends ToolDetailsInputs {
		private Map<String, String> options = new HashMap<String, String>();

		private static final int OPTION_LABEL = 0;
		private static final int OPTION_KEY = 1;

		@JsonProperty("options")
		protected void setOptions(final List<List<String>> optionsList) {
			// The response actually contains 3 elements per list, but I only
			// care about two:
			// * the label (position 0), and
			// * the internal key (position 1) that's Galaxy uses for the label.
			for (List<String> currentOptions : optionsList) {
				options.put(currentOptions.get(OPTION_KEY),
						currentOptions.get(OPTION_LABEL));
			}
		}

		public Map<String, String> getOptions() {
			return this.options;
		}

	}
}
