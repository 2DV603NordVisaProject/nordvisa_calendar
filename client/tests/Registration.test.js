import React from "react";
import { shallow } from "enzyme";
import Registration from "../src/components/Registration";
import en from "../src/i18n/en";

describe('Registration', () => {
  let wrapper;
  const context = { language: en };
  const registration = {
    organization: {
      name: "",
      changePending: "",
    }
  };
  const onChange = jest.fn();

  beforeEach(() => {
    wrapper = shallow(<Registration onInputChange={ onChange } registration={ registration }/>,  { context });
  });

  it("should be defined", () => {
    expect(wrapper).toBeDefined();
  });

  it("should render", () => {
    expect(wrapper.find("div").length).toBeGreaterThan(value);
  });

  it("should have a select", () => {
    expect(wrapper.find("select").length).toBe(1);
  });
  it("should have 3 options", () => {
    expect(wrapper.find("option").length).toBe(3);
  });
  it("should have onChange prop", () => {
    expect(wrapper.props().onInputChange).toBeDefined();
  });

  it("should have registration prop", () => {
    expect(wrapper.props().registration).toBeDefined();
  });
});
