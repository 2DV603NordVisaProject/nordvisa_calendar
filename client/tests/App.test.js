import React from "react";
import ReactDom from "react-dom"
import { shallow } from "enzyme";
import App from "../src/App";

describe('App', () => {
  let wrapper;

  beforeEach(() => {
    wrapper = shallow(<App/>)
  });

  it("should render", () => {
    expect(true).toBe(true);
  })
});
