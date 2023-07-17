/*
 * Copyright © 2021 camunda services GmbH (info@camunda.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.camunda.zeebe.process.test.inspections;

import io.camunda.zeebe.process.test.assertions.BpmnAssert;
import io.camunda.zeebe.process.test.filters.StreamFilter;

/**
 * This class is a utility class for finding process instances that have been started without being
 * triggered by a command send by a client (e.g. timer start events or call activities).
 */
public class InspectionUtility {

  public static ProcessEventInspections findProcessEvents() {
    return new ProcessEventInspections(
        StreamFilter.processEventRecords(BpmnAssert.getRecordStream()));
  }

  public static ProcessInstanceInspections findProcessInstances() {
    return new ProcessInstanceInspections(
        StreamFilter.processInstance(BpmnAssert.getRecordStream()));
  }

  public static DeploymentInspections findProcessDefinitions() {
    return new DeploymentInspections(StreamFilter.deploymentRecords(BpmnAssert.getRecordStream()));
  }
}
