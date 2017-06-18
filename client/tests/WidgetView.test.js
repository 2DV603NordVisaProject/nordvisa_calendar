import React from 'react';
import { shallow } from 'enzyme';
import en from '../src/i18n/en';
import WidgetView from '../src/components/WidgetView';
import ProvinceSelect from '../src/components/ProvinceSelect';
import PageTitle from '../src/components/PageTitle';

// TODO; Integration test with child components

describe('WidgetView', () => {
  let wrapper;
  const context = { language: en };

  beforeEach(() => {
    wrapper = shallow(<WidgetView />, { context });
  });

  it('should render component', () => {
    expect(wrapper.find('.widget-view').length).toBe(1);
  });

  it('should contain PageTitle-component', () => {
    expect(wrapper.find(PageTitle).length).toBe(1);
  });

  it('should not show ProvinceSelect', () => {
    expect(wrapper.find('.hidden').length).toBe(1);
  });

  describe('user clicks generate button without any region selected', () => {
    beforeEach(() => {
      const btn = wrapper.find('.btn-primary').first();
      btn.simulate('click', {
        preventDefault: () => {},
      });
    });

    it('should update fieldErrors state', () => {
      expect(wrapper.state().fieldErrors.length).toBe(1);
    });
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

    describe("user clicks 'generate'", () => {
      beforeEach(() => {
        const btn = wrapper.find('.btn-primary').first();
        btn.simulate('click', {
          preventDefault: () => {},
        });
      });

      it('should show code code-code', () => {
        expect(wrapper.find('.code-container').length).toBe(1);
      });
    });
  });
});
