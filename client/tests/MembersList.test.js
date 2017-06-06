import React from "react";
import { shallow } from "enzyme";
import MembersList from "../src/components/MembersList";
import Member from "../src/components/Member";
import en from "../src/i18n/en";

describe('MembersList', () => {
  let wrapper;
  const context = { language: en};
  const members = [1, 2];
  const onChange = jest.fn()

  beforeEach(() => {
    wrapper = shallow(<MembersList members={members} onChange={onChange}/>, { context });
  });

  it("should be defined", () => {
    expect(wrapper).toBeDefined();
  });

  it("should be rendered", () => {
    expect(wrapper.find("div").length).toBeGreaterThan(0);
  });

  it("should pass member prop to Member component", () => {
    const member = wrapper.find("Member").first();
    expect(member.props().member).toBeDefined();
  });

  it("should pass onChange prop to Member Component", () => {
    const member = wrapper.find("Member").first();
    expect(member.props().onChange).toBeDefined();
  });

  it("Should display Member-Components", () => {
    expect(wrapper.find("Member").length).toBe(2);
  });
});
