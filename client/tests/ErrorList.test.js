/*
- is defined
- renders
- prints error messages
- contains the correct amount of error messages
 */

import React from "react";
import { shallow } from "enzyme";
import ErrorList from "../src/components/ErrorList";

describe('ErrorList', () => {
  let wrapper;
  const errors = ["sample error one", "sample error two"];

  beforeEach(() => {
    wrapper = shallow(<ErrorList errors={ errors }/>);
  });

  it("should be defined", () => {
    expect(wrapper).toBeDefined();
  });

  it("should be rendered", () => {
    expect(wrapper.find("ul").length).toBe(1);
  });

  it("should contain error message", () => {
    const error = wrapper.find("li").at(2);
    expect(error.text()).toBe(errors[1]);
  });

  it("should print all error message", () => {
    expect(wrapper.find("li").length).toBe(2);
  });
});
