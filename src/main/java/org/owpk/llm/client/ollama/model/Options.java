package org.owpk.llm.client.ollama.client.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.ReflectiveAccess;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;
import lombok.Data;

@Data
@Introspected
@ReflectiveAccess
@Builder
@Serdeable
public class Options {
    /**
     * Enable Mirostat sampling for controlling perplexity. (default: 0, 0 =
     * disabled, 1 = Mirostat, 2 = Mirostat 2.0)
     */
    @Builder.Default
    private int mirostat = 0;

    /**
     * Influences how quickly the algorithm responds to feedback from the generated
     * text.
     * A lower learning rate will result in slower adjustments, while a higher
     * learning rate
     * will make the algorithm more responsive. (Default: 0.1)
     */
    @Builder.Default
    private float mirostat_eta = 0.1f;

    /**
     * Controls the balance between coherence and diversity of the output.
     * A lower value will result in more focused and coherent text. (Default: 5.0)
     */
    @Builder.Default
    private float mirostat_tau = 5.0f;

    /**
     * Sets the size of the context window used to generate the next token.
     * (Default: 2048)
     */
    @Builder.Default
    private int num_ctx = 4096;

    /**
     * Sets how far back for the model to look back to prevent repetition.
     * (Default: 64, 0 = disabled, -1 = num_ctx)
     */
    @Builder.Default
    private int repeat_last_n = 64;

    /**
     * Sets how strongly to penalize repetitions. A higher value (e.g., 1.5) will
     * penalize
     * repetitions more strongly, while a lower value (e.g., 0.9) will be more
     * lenient. (Default: 1.1)
     */
    @Builder.Default
    private float repeat_penalty = 1.1f;

    /**
     * The temperature of the model. Increasing the temperature will make the model
     * answer more creatively. (Default: 0.8)
     */
    @Builder.Default
    private float temperature = 0.8f;

    /**
     * Sets the random number seed to use for generation. Setting this to a specific
     * number
     * will make the model generate the same text for the same prompt. (Default: 0)
     */
    @Builder.Default
    private int seed = 42;

    /**
     * Sets the stop sequences to use. When this pattern is encountered the LLM will
     * stop
     * generating text and return. Multiple stop patterns may be set by specifying
     * multiple
     * separate stop parameters in a modelfile.
     */
    @Builder.Default
    private String stop = "AI assistant:";

    /**
     * Maximum number of tokens to predict when generating text. (Default: -1,
     * infinite generation)
     */
    @Builder.Default
    private int num_predict = -1;

    /**
     * Reduces the probability of generating nonsense. A higher value (e.g. 100)
     * will give more
     * diverse answers, while a lower value (e.g. 10) will be more conservative.
     * (Default: 40)
     */
    @Builder.Default
    private int top_k = 40;

    /**
     * Works together with top-k. A higher value (e.g., 0.95) will lead to more
     * diverse text,
     * while a lower value (e.g., 0.5) will generate more focused and conservative
     * text. (Default: 0.9)
     */
    @Builder.Default
    private float top_p = 0.9f;

    /**
     * Alternative to the top_p, and aims to ensure a balance of quality and
     * variety. The parameter p
     * represents the minimum probability for a token to be considered, relative to
     * the probability of
     * the most likely token. For example, with p=0.05 and the most likely token
     * having a probability
     * of 0.9, logits with a value less than 0.045 are filtered out. (Default: 0.0)
     */
    @Builder.Default
    private float min_p = 0.05f;
}
