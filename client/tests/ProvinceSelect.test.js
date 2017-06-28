import React from 'react';
import { shallow } from 'enzyme';
import en from '../src/i18n/en';
import ProvinceSelect from '../src/components/ProvinceSelect';
import NorwaySelect from '../src/components/NorwaySelect';
import DenmarkSelect from '../src/components/DenmarkSelect';
import SwedenSelect from '../src/components/SwedenSelect';
import IcelandSelect from '../src/components/IcelandSelect';


describe('ProvinceSelect', () => {
  let wrapper;
  const context = { language: en };

  beforeEach(() => {
    wrapper = shallow(<ProvinceSelect />, { context });
  });

  it('should be defined', () => {
    expect(wrapper).toBeDefined();
  });

  describe('prop region is sweden', () => {
    beforeEach(() => {
      wrapper = shallow(<ProvinceSelect region="sweden" />, { context });
    });

    it('should render SwedenSelect', () => {
      expect(wrapper.find(SwedenSelect).length).toBe(1);
    });
  });

  describe('prop region is norway', () => {
    beforeEach(() => {
      wrapper = shallow(<ProvinceSelect region="norway" />, { context });
    });

    it('should render NorwaySelect', () => {
      expect(wrapper.find(NorwaySelect).length).toBe(1);
    });
  });

  describe('prop region is denmark', () => {
    beforeEach(() => {
      wrapper = shallow(<ProvinceSelect region="denmark" />, { context });
    });

    it('should render DenmarkSelect', () => {
      expect(wrapper.find(DenmarkSelect).length).toBe(1);
    });
  });

  describe('prop region is iceland', () => {
    beforeEach(() => {
      wrapper = shallow(<ProvinceSelect region="iceland" />, { context });
    });

    it('should render IcelandSelect', () => {
      expect(wrapper.find(IcelandSelect).length).toBe(1);
    });
  });
});
