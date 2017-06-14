/*
- change should update state
- mock client
- should trigger 4 different errors - put them in state
- errors should be printed - errors should be passed as prop
 */
import React from "react";
import { shallow } from "enzyme";
import en from "../src/i18n/en";
import ErrorList from "../src/components/ErrorList";
import UpdatePassword from "../src/components/UpdatePassword";

describe('UpdatePassword', () => {
  let wrapper;
  const context = { language: en};

  beforeEach(() => {
    wrapper = shallow(<UpdatePassword/>, { context });
  });

  it("should be defined", () => {
    expect(wrapper).toBeDefined();
  });

  it("should render", () => {
    expect(wrapper.find("div").length).toBeGreaterThan(0);
  });

  it("should have errorlist", () => {
    expect(wrapper.find("ErrorList").length).toBe(1);
  });

  it("should have a form", () => {
    expect(wrapper.find("form").length).toBe(1);
  });

  it("should have 4 input", () => {
    expect(wrapper.find("input").length).toBe(4);
  });

  describe('user enters password', () => {

    beforeEach(() => {
     const oldPasswordInput;
     const newPasswordInput;
     const confirmPasswordInput;
    });
  });
});
