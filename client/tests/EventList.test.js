import React from "react";
import { shallow } from "enzyme";
import EventsList from "../src/components/EventsList";
import Event from "../src/components/Event";
import en from "../src/i18n/en";

describe('EventsList', () => {
  let wrapper;
  const context = { language: en};
  const events = [1, 2];
  const onDelete = jest.fn();

  beforeEach(() => {
    wrapper = shallow(<EventsList events={events} delete={onDelete}/>, { context });
  });

  it("should be defined", () => {
    expect(wrapper).toBeDefined();
  });

  it("should render", () => {
    expect(wrapper.find("div").length).toBeGreaterThan(0);
  });

  it("should display Events", () => {
    expect(wrapper.find("Event").length).toBe(2);
  });

  it("should send Event an event prop", () => {
    const Event = wrapper.find("Event").first();
    expect(Event.props().event).toBeDefined();
  });

  it("should send Event an delete prop", () => {
    const Event = wrapper.find("Event").first();
    expect(Event.props().delete).toBeDefined();
  });
});
