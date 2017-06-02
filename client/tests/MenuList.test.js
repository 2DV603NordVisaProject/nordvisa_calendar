import React from "react";
import { shallow } from "enzyme";
import MenuList from "../src/components/MenuList";
import en from "../src/i18n/en";


describe('MenuList', () => {
  let wrapper;

  let context = { language: en };

  beforeEach(() => {
    wrapper = shallow(<MenuList/>, { context });
  });

  describe('user is not logged in', () => {

    const Client = {
      getUserLevel: jest.fn(),
    }

    it("should have 4 menu items", () => {
      // Body...
      });
  });
});
