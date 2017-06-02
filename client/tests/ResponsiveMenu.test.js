import React from "react";
import ResponsiveMenu from "../src/components/ResponsiveMenu";
import { shallow } from "enzyme";

describe('ResponsiveMenu', () => {
  let wrapper;

  beforeEach(() => {
    wrapper = shallow(<ResponsiveMenu/>);
  });

  it("menu state should not be collapsed", () => {
    expect(wrapper.state().menu.isCollapsed).toBe(false);
    });

  it("should render menu button", () => {
    expect(wrapper.find(".menu-btn").length).toBe(1);
  });

  it("should not show menu", () => {
    expect(wrapper.find(".expand").length).toBe(0);
  });

  it("should not change menu button", () => {
    expect(wrapper.find(".change").length).toBe(0);
  });

  describe('user clicks menu button', () => {

    beforeEach(() => {
      const btn = wrapper.find(".menu-btn").first();
      btn.simulate("click", {
        preventDefault: () => {},
      });
    });

    it("should update menu state", () => {
      expect(wrapper.state().menu.isCollapsed).toBe(true);
      });

    it("should have updated menu button", () => {
      expect(wrapper.find(".change").length).toBe(1);
    });

    it("should show menu", () => {
      expect(wrapper.find(".expand").length).toBe(1);
    });

  });
});
