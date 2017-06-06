/*
- should be defined
- should render
 */
import React from "react";
import { shallow } from "enzyme";
import MapMarker from "../src/components/MapMarker";

describe('MapMarker', () => {
  let wrapper;

  beforeEach(() => {
    wrapper = shallow(<MapMarker/>);
  });

  it("should be defined", () => {
    expect(wrapper).toBeDefined();
  });

  it("should render", () => {
    expect(wrapper.find("div").length).toBeGreaterThan(0);
  });
});
