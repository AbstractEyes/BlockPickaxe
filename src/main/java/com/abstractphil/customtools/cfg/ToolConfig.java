package com.abstractphil.customtools.cfg;

import java.util.Map;

import lombok.Data;

@Data
public class ToolConfig {
	private Map<String, ToolEffectData> effects;
}
