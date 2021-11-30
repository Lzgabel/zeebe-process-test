package io.camunda.zeebe.bpmnassert.testengine;

import io.camunda.zeebe.engine.processing.message.command.PartitionCommandSender;
import io.camunda.zeebe.engine.processing.message.command.SubscriptionCommandMessageHandler;
import io.camunda.zeebe.util.buffer.BufferWriter;
import org.agrona.concurrent.UnsafeBuffer;

public class PartitionCommandSenderImpl implements PartitionCommandSender {

  private final SubscriptionCommandMessageHandler subscriptionHandler;
  private final int partitionId;

  public PartitionCommandSenderImpl(
      final SubscriptionCommandMessageHandler subscriptionHandler, final int partitionId) {
    this.subscriptionHandler = subscriptionHandler;
    this.partitionId = partitionId;
  }

  @Override
  public boolean sendCommand(final int receiverPartitionId, final BufferWriter command) {
    if (receiverPartitionId != partitionId) {
      throw new RuntimeException(
          String.format(
              "Expected receiverPartitionId to be %d, but was %d",
              partitionId, receiverPartitionId));
    }

    final byte[] bytes = new byte[command.getLength()];
    final UnsafeBuffer commandBuffer = new UnsafeBuffer(bytes);
    command.write(commandBuffer, 0);
    subscriptionHandler.apply(bytes);
    return true;
  }
}
