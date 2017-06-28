import React from 'react';
import { mount } from 'enzyme';
import en from '../src/i18n/en';
import GenerateWidgetPage from '../src/pages/GenerateWidgetPage';
import ProvinceSelect from '../src/components/ProvinceSelect';
import PageTitle from '../src/components/PageTitle';

describe('GenerateWidgetPage', () => {
  let wrapper;
  const context = { language: en };

  beforeEach(() => {
    wrapper = mount(<GenerateWidgetPage />, { context });
  });

  it('should render component', () => {
    expect(wrapper.find('.widget-view').length).toBe(1);
  });

  it('should contain PageTitle-component', () => {
    expect(wrapper.find(PageTitle).length).toBe(1);
  });

  it('should contain ProvinceSelect-component', () => {
    expect(wrapper.find(ProvinceSelect).length).toBe(1);
  });

  describe('user selects a country', () => {
    const value = 'sweden';

    beforeEach(() => {
      const select = wrapper.find('select').first();
      select.simulate('change', {
        target: {
          value,
          name: 'region',
        },
      });
    });

    it("should update state 'fields.region' with value", () => {
      expect(wrapper.state().fields.region).toEqual(value);
    });

    it('should render SelectProvince', () => {
      expect(wrapper.find(ProvinceSelect).length).toBe(1);
    });

    it('should display SelectProvince', () => {
      expect(wrapper.find('.hidden').length).toBe(0);
    });
  });
});
